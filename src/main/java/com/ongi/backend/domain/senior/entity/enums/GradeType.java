package com.ongi.backend.domain.senior.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GradeType {
    GRADE_NO(0, "등급없음"),
    GRADE_1(1, "1등급"),
    GRADE_2(2, "2등급"),
    GRADE_3(3, "3등급"),
    GRADE_4(4, "4등급"),
    GRADE_5(5, "5등급"),
    GRADE_SUPPORT(6, "인지지원 등급");

    private final int code;  // 숫자로 저장될 경우 대비
    private final String description;  // 프론트에 표시할 한글명

    // 한글 설명으로 Enum 변환
    public static GradeType fromDescription(String description) {
        return Arrays.stream(GradeType.values())
                .filter(grade -> grade.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SeniorGrade description: " + description));
    }

    // 숫자로 Enum 변환
    public static GradeType fromCode(int code) {
        return Arrays.stream(GradeType.values())
                .filter(grade -> grade.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid SeniorGrade code: " + code));
    }
}
