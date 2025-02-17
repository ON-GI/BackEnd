package com.ongi.backend.domain.senior.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResidenceType {
    ALONE("독거"),
    WITH_SPOUSE_HOME("배우자와 동거, 돌봄 시간 중에 집에 있음"),
    WITH_SPOUSE_AWAY("배우자와 동거, 돌봄 시간 중에 자리 비움"),
    WITH_FAMILY_HOME("다른 가족과 동거, 돌봄 시간 중 집에 있음"),
    WITH_FAMILY_AWAY("다른 가족과 동거, 돌봄 시간 중 자리 비움");

    private final String description;  // 프론트 표시용 한글 설명

    public static ResidenceType fromDescription(String description) {
        for (ResidenceType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ResidenceType description: " + description);
    }
}