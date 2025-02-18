package com.ongi.backend.domain.senior.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeniorErrorCase implements ErrorCase {

    SENIOR_NOT_FOUND(1000, 400, "어르신을 찾을 수 없습니다."),

    SENIOR_CENTER_UNMATCHED(1001, 400, "센터에서 관리하는 어르신이 아닙니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
