package com.curpick.CurPick.global.exception;

public class CustomException extends RuntimeException {

    private final String errorCode;

    public CustomException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}