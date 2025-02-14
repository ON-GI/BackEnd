package com.ongi.backend.domain.caregiver.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaregiverErrorCase implements ErrorCase {

    DUPLICATE_LOGIN_ID(400, 400, "이미 존재하는 아이디입니다."),
    CAREGIVER_NOT_FOUND(400, 400, "요양보호사를 찾을 수 없습니다."),
    WORK_CONDITION_NOT_FOUND(400, 400, "근무 조건을 찾을 수 없습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}