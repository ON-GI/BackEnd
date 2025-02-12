package com.ongi.backend.domain.caregiver.entity;

import lombok.Getter;

@Getter
public enum CaregiverWorkConditionPayType {
    HOURLY("시급"),
    DAILY("일급"),
    MONTHLY("월급");

    private final String description;

    CaregiverWorkConditionPayType(String description) {
        this.description = description;
    }
}
