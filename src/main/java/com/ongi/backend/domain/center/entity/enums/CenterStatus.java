package com.ongi.backend.domain.center.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CenterStatus {
    NOT_VERIFIED("인증 안됨"),
    PENDING_VERIFICATION("인증 대기 중"),
    VERIFIED("인증 완료");

    private final String description;

    public static CenterStatus fromDescription(String description) {
        for (CenterStatus status : values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown description: " + description);
    }
}
