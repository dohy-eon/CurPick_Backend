package com.curpick.CurPick.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank
    private String username;

    @Schema(description = "비밀번호", example = "Password123!")
    @NotBlank
    private String password;
}