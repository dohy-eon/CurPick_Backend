package com.jobfeed.JobFeed.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EmailVerifyDto {
    @Schema(
            description = "이메일 주소",
            example = "user@example.com"
    )
    @NotBlank
    private String email;

    @Schema(
            description = "이메일로 발송된 인증번호 (6자리)",
            example = "123456"
    )
    @NotBlank
    private String authCode;
}
