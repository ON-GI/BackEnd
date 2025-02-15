package com.ongi.backend.domain.auth.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCase implements ErrorCase {
    WRONG_PASSWORD(400, 400, "비밀번호가 일치하지 않습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
