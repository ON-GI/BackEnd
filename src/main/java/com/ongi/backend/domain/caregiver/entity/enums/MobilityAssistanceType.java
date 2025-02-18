package com.ongi.backend.domain.caregiver.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MobilityAssistanceType {
    INDEPENDENT("스스로 이동 가능"),
    SUPPORT_WALKING("이동 시 부축 도움"),
    WHEELCHAIR_ASSIST("휠체어 이동 보조"),
    IMMOBILE("거동 불가"),
    NOT_APPLICABLE("해당 없음");

    private final String description;

    public static MobilityAssistanceType fromString(String value) {
        return Arrays.stream(MobilityAssistanceType.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_MOBILITY_ASSISTANCE));
    }
}
