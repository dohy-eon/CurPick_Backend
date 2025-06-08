package com.curpick.CurPick.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenBox {
    private String accessToken;
    private String refreshToken;
    private String authority;
}
