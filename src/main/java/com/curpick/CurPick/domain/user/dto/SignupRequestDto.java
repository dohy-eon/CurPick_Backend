package com.curpick.CurPick.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Schema(
            description = "사용자 닉네임",
            example = "홍길동"
    )
    @NotBlank
    private String nickname;

    @Schema(
            description = "사용자 아이디",
            example = "user123"
    )
    @NotBlank
    private String username;

    @Schema(
            description = "비밀번호 (8~20자, 영문+숫자+특수문자)",
            example = "Password123!"
    )
    @NotBlank
    private String password;

    @Schema(
            description = "비밀번호 확인",
            example = "Password123!"
    )
    @NotBlank
    private String passwordConfirm;

    @Schema(
            description = "이메일 주소",
            example = "user@example.com"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "이메일 인증 완료 여부",
            example = "true"
    )
    @NotNull
    private Boolean emailVerified;
}
