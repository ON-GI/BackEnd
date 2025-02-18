package com.ongi.backend.domain.caregiver.exception;

import com.ongi.backend.common.exception.ErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaregiverErrorCase implements ErrorCase {

    DUPLICATE_LOGIN_ID(400, 400, "이미 존재하는 아이디입니다."),
    CAREGIVER_NOT_FOUND(404, 404, "요양보호사를 찾을 수 없습니다."),
    WORK_CONDITION_NOT_FOUND(400, 400, "근무 조건을 찾을 수 없습니다."),
    INVALID_LICENSE_TYPE(400, 400, "불가능한 자격증명입니다."),
    INVALID_CAREER_TYPE(400, 400, "불가능한 경력 조건입니다."),
    INVALID_DAILY_LIVING_ASSISTANCE(400, 400, "불가능한 일상생활 보조 타입입니다."),
    INVALID_MOBILITY_ASSISTANCE(400, 400, "불가능한 이동 보조 타입입니다."),
    INVALID_FEEDING_ASSISTANCE(400, 400, "불가능한 식사 보조 타입입니다."),
    INVALID_TOILETING_ASSISTANCE(400, 400, "불가능한 배변 보조 티입입니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;
}