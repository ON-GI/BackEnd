package com.ongi.backend.domain.center.entity.enums;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.center.exception.CenterErrorCase;

import java.util.Arrays;

public enum CenterGrade {
    A, B, C, D, E;

    public static CenterGrade fromString(String value) {
        return Arrays.stream(CenterGrade.values())
                .filter(type -> type.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new ApplicationException(CenterErrorCase.INVALID_CENTER_GRADE));
    }
}
