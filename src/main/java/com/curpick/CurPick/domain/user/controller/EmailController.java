package com.curpick.CurPick.domain.user.controller;

import com.curpick.CurPick.domain.user.dto.EmailRequestDto;
import com.curpick.CurPick.domain.user.dto.EmailVerifyDto;
import com.curpick.CurPick.domain.user.service.EmailService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "이메일 인증 코드 발송", description = "입력된 이메일로 6자리 인증 코드를 전송합니다. 인증 코드는 5분간 유효합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이메일 전송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "이메일 누락",
                                    value = "{ \"code\": \"E001\", \"message\": \"이메일은 필수입니다.\" }"
                            )
                    )
            )
    })
    @PostMapping("/send")
    public ResponseEntity<Void> sendAuthCode(@RequestBody @Valid EmailRequestDto request) {
        emailService.sendAuthCode(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 인증 코드 확인", description = "사용자가 입력한 인증 코드가 유효한지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "인증 성공"),
            @ApiResponse(responseCode = "400", description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "코드 불일치",
                                    value = "{ \"code\": \"E002\", \"message\": \"인증 코드가 유효하지 않습니다.\" }"
                            )
                    )
            )
    })
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyAuthCode(@RequestBody @Valid EmailVerifyDto request) {
        emailService.verifyCode(request.getEmail(), request.getAuthCode());
        return ResponseEntity.ok().build();
    }
}