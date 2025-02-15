package com.ongi.backend.domain.caregiver.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaregiverWorkConditionPayType {
    HOURLY("시급"),
    DAILY("일급"),
    MONTHLY("월급");

    private final String description;
}
