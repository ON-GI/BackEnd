package com.ongi.backend.domain.caregiver.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ToiletingAssistanceType {
    INDEPENDENT("스스로 식사 가능"),
    MEAL_PREPARATION("식사 차려드리기"),
    COOKING_REQUIRED("죽, 반찬 등 요리 필요"),
    TUBE_FEEDING("경관식 보조"),
    NOT_APPLICABLE("해당 없음");

    private final String description;

    public static ToiletingAssistanceType fromString(String value) {
        return Arrays.stream(ToiletingAssistanceType.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_TOILETING_ASSISTANCE));
    }
}
