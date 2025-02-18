package com.ongi.backend.domain.senior.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeniorCareType {
    TOILETING("배변보조"),
    FEEDING("식사보조"),
    MOBILITY("이동보조"),
    DAILY_LIVING("일상생활 지원"),
    NOT_APPLICABLE("해당 없음");

    private final String description;
}