package com.ongi.backend.domain.auth.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
    ROLE_CAREGIVER("요양 보호사"),
    ROLE_CENTER("센터 관리자");

    private final String description;
}
