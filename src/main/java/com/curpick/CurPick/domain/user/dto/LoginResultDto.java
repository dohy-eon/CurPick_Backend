package com.curpick.CurPick.domain.user.dto;

import com.curpick.CurPick.global.auth.dto.TokenBox;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResultDto {
    private TokenBox tokenBox;
    private LoginResponseDto user;
}
