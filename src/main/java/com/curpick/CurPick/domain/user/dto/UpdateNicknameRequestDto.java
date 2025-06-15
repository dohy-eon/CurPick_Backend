package com.curpick.CurPick.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDto {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickname;
}