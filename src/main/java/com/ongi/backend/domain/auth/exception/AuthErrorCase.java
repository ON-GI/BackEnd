package com.ongi.backend.domain.auth.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCase implements ErrorCase {
    WRONG_PASSWORD(400, 400, "비밀번호가 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN(400, 400, "유효하지 않은 리프레시토큰입니다."),
    REFRESH_TOKEN_NOT_EQUAL(400, 400, "다른 기기에서 로그인했습니다."),
    REFRESH_TOKEN_NOT_FOUND(404, 404, "리프레시 토큰 정보를 찾을 수 없습니다."),
    INVALID_AUTHORITY(400, 400, "유효하지 않은 권한입니다."),
    NOT_APPROVAL(400, 400, "승인되지 않은 회원입니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
