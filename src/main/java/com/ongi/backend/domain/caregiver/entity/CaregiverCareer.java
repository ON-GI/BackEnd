package com.ongi.backend.domain.caregiver.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CaregiverCareer {
    NONE("경력 없음"),
    LESS_THAN_ONE_YEAR("1년 이하"),
    ONE_TO_THREE_YEARS("1년 ~ 3년 미만"),
    THREE_TO_FIVE_YEARS("3년 ~ 5년 미만"),
    MORE_THAN_FIVE_YEARS("5년 이상");

    private final String description;
}
