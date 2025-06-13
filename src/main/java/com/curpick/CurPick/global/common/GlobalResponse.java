package com.curpick.CurPick.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalResponse<T> {
    private String code;
    private String message;
    private T data;
}