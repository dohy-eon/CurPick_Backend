package com.curpick.CurPick.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class EmailRequestDto {
    @Schema(
            description = "이메일 주소",
            example = "user@example.com"
    )
    @Email
    @NotBlank
    private String email;
}
