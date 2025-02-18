package com.ongi.backend.domain.caregiver.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ToiletingAssistanceType {
    INDEPENDENT("스스로 배변 가능"),
    OCCASIONAL_HELP("가끔 대소변 실수 시 도움"),
    DIAPER_CARE("기저귀 케어 필요"),
    CATHETER_OR_COLOSTOMY("유치도뇨/방광루/장루"),
    NOT_APPLICABLE("해당 없음");

    private final String description;

    public static ToiletingAssistanceType fromString(String value) {
        return Arrays.stream(ToiletingAssistanceType.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.INVALID_TOILETING_ASSISTANCE));
    }
}
