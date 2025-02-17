package com.ongi.backend.domain.centerstaff.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CenterStaffErrorCase implements ErrorCase {

    CENTER_INFO_REQUIRED(400, 400, "센터 정보를 입력하세요"),
    CENTER_MANAGER_EXISTS(400, 400, "이미 센터장이 존재합니다."),
    CENTER_STAFF_NOT_FOUND(404, 404, "존재하지 않는 회원입니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}
