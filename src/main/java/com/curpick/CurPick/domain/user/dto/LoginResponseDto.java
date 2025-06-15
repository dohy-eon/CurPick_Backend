package com.curpick.CurPick.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String nickname;
    private String username;
    private String email;
    private String authority;
}