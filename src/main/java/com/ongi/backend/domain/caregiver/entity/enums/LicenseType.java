package com.ongi.backend.domain.caregiver.entity.enums;
import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LicenseType {
    CAREGIVER("요양보호사 자격증"),
    SOCIAL_WORKER("사회복지사 자격증"),
    NURSE_AIDE("간호조무사 자격증");

    private final String description;

    public static LicenseType fromString(String value) {
        return Arrays.stream(LicenseType.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_LICNESE_TYPE));
    }
}
