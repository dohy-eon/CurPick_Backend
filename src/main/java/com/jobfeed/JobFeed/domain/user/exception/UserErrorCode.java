package com.jobfeed.JobFeed.domain.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {
    DUPLICATE_USERNAME("U001", "이미 사용 중인 아이디입니다."),
    DUPLICATE_EMAIL("U002", "이미 사용 중인 이메일입니다."),
    EMAIL_VERIFICATION_REQUIRED("U003", "이메일 인증이 필요합니다."),
    PASSWORD_MISMATCH("U004", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_REQUEST("U005", "요청한 값이 올바르지 않습니다."),
    TOKEN_EXPIRED("U006", "토큰이 만료되었습니다."),
    INVALID_CREDENTIALS("U007", "아이디 또는 비밀번호가 일치하지 않습니다.");

    private final String code;
    private final String message;
}
