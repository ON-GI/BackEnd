package com.ongi.backend.domain.auth.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.caregiver.entity.enums.CaregiverCareer;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Authority {
    ROLE_CAREGIVER("요양 보호사"),
    ROLE_CENTER("센터 관리자");

    private final String description;

    public static Authority fromString(String value) {
        return Arrays.stream(Authority.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(AuthErrorCase.INVALID_AUTHORITY));
    }
}
