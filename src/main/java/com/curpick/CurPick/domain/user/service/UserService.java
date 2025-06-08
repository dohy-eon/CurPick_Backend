package com.curpick.CurPick.domain.user.service;

import com.curpick.CurPick.domain.user.dto.LoginRequestDto;
import com.curpick.CurPick.domain.user.dto.SignupRequestDto;
import com.curpick.CurPick.domain.user.entity.User;
import com.curpick.CurPick.domain.user.exception.UserErrorCode;
import com.curpick.CurPick.domain.user.repository.UserRepository;
import com.curpick.CurPick.global.auth.dto.TokenBox;
import com.curpick.CurPick.global.auth.jwt.JwtTokenProvider;
import com.curpick.CurPick.global.auth.userdetails.UserDetailsImpl;
import com.curpick.CurPick.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.curpick.CurPick.domain.user.exception.UserErrorCode.*;

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
        // 실제 구현 시 JWT 유틸 클래스에서 토큰 생성 필요
        return TokenBox.builder()
                .accessToken("new-access-token")
                .refreshToken("new-refresh-token")
                .authority(userDetails.getAuthorities().toString())
                .build();
    }

    @Transactional(readOnly = true)
    public TokenBox login(LoginRequestDto request) {
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

        return TokenBox.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .authority(user.getRole().name())
                .build();
    }

}