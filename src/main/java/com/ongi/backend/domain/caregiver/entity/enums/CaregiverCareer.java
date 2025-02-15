package com.ongi.backend.domain.caregiver.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CaregiverCareer {
    NONE("경력 없음"),
    LESS_THAN_ONE_YEAR("1년 이하"),
    ONE_TO_THREE_YEARS("1년 ~ 3년 미만"),
    THREE_TO_FIVE_YEARS("3년 ~ 5년 미만"),
    MORE_THAN_FIVE_YEARS("5년 이상");

    private final String description;

    public static CaregiverCareer fromString(String value) {
        return Arrays.stream(CaregiverCareer.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_CAREER_TYPE));
    }
}
