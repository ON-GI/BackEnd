package com.ongi.backend.domain.caregiver.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaregiverErrorCase implements ErrorCase {

    CAREGIVER_NOT_FOUND(400, "요양보호사를 찾을 수 없습니다."),
    WORK_CONDITION_NOT_FOUND(400, "근무 조건을 찾을 수 없습니다.");

    private final Integer httpStatusCode = 400; // 모든 예외를 400으로 통일
    private final Integer errorCode;
    private final String message;
}