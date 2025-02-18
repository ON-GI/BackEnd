package com.ongi.backend.domain.matching.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchingErrorCase implements ErrorCase {
    MATCHING_NOT_FOUND(1000, 400, "매칭 정보를 찾을 수 없습니다."),
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
