package com.ongi.backend.domain.auth.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    CareGiver("요양 보호사"),
    Center("센터 관리자");

    private final String description;
}
