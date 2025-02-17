package com.ongi.backend.domain.center.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CenterErrorCase implements ErrorCase {

    CENTER_NOT_FOUND(1000, 400, "센터를 찾을 수 없습니다."),

    CENTER_EMAIL_NOT_EXIST(1001, 400, "센터 이메일이 등록되어 있지 않습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
