package com.ongi.backend.common.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Authority {
    ROLE_CAREGIVER("요양 보호사"),
    ROLE_CENTER_MANAGER("센터장"),
    ROLE_SOCIAL_WORKER("사회 복지사");

    private final String description;

    public static Authority fromString(String value) {
        return Arrays.stream(Authority.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(AuthErrorCase.INVALID_AUTHORITY));
    }
}
