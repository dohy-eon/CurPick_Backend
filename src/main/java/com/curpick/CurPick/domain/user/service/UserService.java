package com.curpick.CurPick.domain.user.service;

import com.curpick.CurPick.domain.user.dto.LoginRequestDto;
import com.curpick.CurPick.domain.user.dto.LoginResponseDto;
import com.curpick.CurPick.domain.user.dto.LoginResultDto;
import com.curpick.CurPick.domain.user.dto.SignupRequestDto;
import com.curpick.CurPick.domain.user.entity.User;
import com.curpick.CurPick.domain.user.exception.UserErrorCode;
import com.curpick.CurPick.domain.user.repository.UserRepository;
import com.curpick.CurPick.global.auth.dto.TokenBox;
import com.curpick.CurPick.global.auth.jwt.JwtTokenProvider;
import com.curpick.CurPick.global.auth.userdetails.UserDetailsImpl;
import com.curpick.CurPick.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

import static com.curpick.CurPick.domain.user.exception.UserErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public void signUp(SignupRequestDto request) {
        // 비밀번호 일치 검증
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new CustomException(
                    PASSWORD_MISMATCH.getCode(),
                    PASSWORD_MISMATCH.getMessage()
            );
        }

        // 아이디 중복 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(
                    DUPLICATE_USERNAME.getCode(),
                    DUPLICATE_USERNAME.getMessage()
            );
        }

        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(
                    DUPLICATE_EMAIL.getCode(),
                    DUPLICATE_EMAIL.getMessage()
            );
        }

        // 이메일 인증 여부 확인
        if (!request.getEmailVerified()) {
            throw new CustomException(
                    EMAIL_VERIFICATION_REQUIRED.getCode(),
                    EMAIL_VERIFICATION_REQUIRED.getMessage()
            );
        }

        // 엔티티 생성 및 저장
        User user = User.builder()
                .nickname(request.getNickname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userRepository.save(user);
    }

    @Transactional
    public TokenBox tokenRotation(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        String newAccessToken = jwtTokenProvider.createAccessToken(user);
        String newRefreshToken = jwtTokenProvider.createRefreshToken(user);

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return TokenBox.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .authority(authorities)
                .build();
    }

    @Transactional(readOnly = true)
    public LoginResultDto login(LoginRequestDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(
                        UserErrorCode.INVALID_CREDENTIALS.getCode(),
                        UserErrorCode.INVALID_CREDENTIALS.getMessage()
                ));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(
                    UserErrorCode.INVALID_CREDENTIALS.getCode(),
                    "아이디 또는 비밀번호가 일치하지 않습니다."
            );
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        log.info("Generated Access Token: {}", accessToken);
        log.info("Generated Refresh Token: {}", refreshToken);

        TokenBox tokenBox = TokenBox.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authority(user.getRole().name())
                .build();

        // TokenBox 생성 확인을 위한 로그
        log.info("Created TokenBox: {}", tokenBox);

        LoginResponseDto userDto = LoginResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .authority(user.getRole().name())
                .build();

        return LoginResultDto.builder()
                .tokenBox(tokenBox)
                .user(userDto)
                .build();
    }

    @Transactional(readOnly = true)
    public List<LoginResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> LoginResponseDto.builder()
                        .id(user.getId())
                        .nickname(user.getNickname())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .authority(user.getRole().name())
                        .build())
                .toList();
    }

    public LoginResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        return LoginResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .username(user.getUsername())
                .email(user.getEmail())
                .authority(user.getRole().name())
                .build();
    }

    /**
     * 사용자 닉네임 수정
     */
    public void updateNickname(Long userId, String newNickname) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        user.updateNickname(newNickname);
        userRepository.save(user); // 변경사항 저장
    }
}