package com.ongi.backend.domain.matching.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchingErrorCase implements ErrorCase {
    MATCHING_NOT_FOUND(1000, 400, "매칭 정보를 찾을 수 없습니다."),

    MATCHING_CENTER_UNMATCHED(1001, 400, "매칭에 등록된 어르신이 센터에 소속되어 있지 않습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
