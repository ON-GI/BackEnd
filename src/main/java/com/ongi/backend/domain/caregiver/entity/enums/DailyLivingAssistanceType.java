package com.ongi.backend.domain.caregiver.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DailyLivingAssistanceType {
    HOUSEKEEPING("청소, 빨래 보조"),
    BATHING_ASSIST("목욕 보조"),
    HOSPITAL_COMPANION("병원 동행"),
    WALKING_EXERCISE("산책, 간단한 운동"),
    EMOTIONAL_SUPPORT("말벗 등 정서 지원"),
    COGNITIVE_STIMULATION("인지 자극 활동"),
    NOT_APPLICABLE("해당 없음");

    private final String description;

    public static DailyLivingAssistanceType fromString(String value) {
        return Arrays.stream(DailyLivingAssistanceType.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_DAILY_LIVING_ASSISTANCE));
    }
}
