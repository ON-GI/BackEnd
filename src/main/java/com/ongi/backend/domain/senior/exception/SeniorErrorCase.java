package com.ongi.backend.domain.senior.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeniorErrorCase implements ErrorCase {

    SENIOR_NOT_FOUND(1000, 400, "어르신을 찾을 수 없습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
