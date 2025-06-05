package com.jobfeed.JobFeed.domain.user.controller;

import com.jobfeed.JobFeed.domain.user.dto.LoginRequestDto;
import com.jobfeed.JobFeed.domain.user.dto.SignupRequestDto;
import com.jobfeed.JobFeed.domain.user.service.UserService;
import com.jobfeed.JobFeed.global.auth.dto.TokenBox;
import com.jobfeed.JobFeed.global.auth.userdetails.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "실패 케이스",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "아이디 중복",
                                            value = "{ \"code\": \"U001\", \"message\": \"이미 사용 중인 아이디입니다.\" }"
                                    ),
                                    @ExampleObject(
                                            name = "이메일 중복",
                                            value = "{ \"code\": \"U002\", \"message\": \"이미 사용 중인 이메일입니다.\" }"
                                    ),
                                    @ExampleObject(
                                            name = "이메일 인증 미완료",
                                            value = "{ \"code\": \"U003\", \"message\": \"이메일 인증이 필요합니다.\" }"
                                    ),
                                    @ExampleObject(
                                            name = "비밀번호 불일치",
                                            value = "{ \"code\": \"U004\", \"message\": \"비밀번호와 비밀번호 확인이 일치하지 않습니다.\" }"
                                    ),
                                    @ExampleObject(
                                            name = "요청값 형식 오류",
                                            value = "{ \"code\": \"U005\", \"message\": \"요청한 값이 올바르지 않습니다.\" }"
                                    )
                            }
                    )
            )
    })
    @PostMapping("/auth/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignupRequestDto request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "토큰 갱신")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공 (Header로 토큰 반환)"),
            @ApiResponse(responseCode = "400", description = "실패 케이스",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "RefreshToken 만료",
                                            value = "{ \"code\": \"U006\", \"message\": \"토큰이 만료되었습니다.\" }"
                                    )
                            }
                    )
            )
    })
    @GetMapping("/auth/token")
    public ResponseEntity<Void> tokenRotation(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        TokenBox tokenBox = userService.tokenRotation(userDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Token", tokenBox.getAccessToken());
        headers.add("Refresh-Token", tokenBox.getRefreshToken());
        headers.add("Authority", tokenBox.getAuthority());

        return ResponseEntity.ok().headers(headers).build();
    }

    @Operation(summary = "로그인")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 (Header로 토큰 반환)"),
            @ApiResponse(responseCode = "401", description = "로그인 실패 (아이디 또는 비밀번호 불일치)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "로그인 실패",
                                    value = "{ \"code\": \"U007\", \"message\": \"아이디 또는 비밀번호가 올바르지 않습니다.\" }"
                            )
                    )
            )
    })
    @PostMapping("/auth/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequestDto request) {
        TokenBox tokenBox = userService.login(request);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Token", tokenBox.getAccessToken());
        headers.add("Refresh-Token", tokenBox.getRefreshToken());
        headers.add("Authority", tokenBox.getAuthority());

        return ResponseEntity.ok().headers(headers).build();
    }
}