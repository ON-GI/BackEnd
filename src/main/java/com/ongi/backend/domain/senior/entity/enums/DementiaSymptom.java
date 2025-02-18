package com.ongi.backend.domain.senior.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum DementiaSymptom {
    WANDERING("집 밖을 나감"),
    MEMORY_LOSS("했던 말을 반복하는 등 단기 기억 장애"),
    CANNOT_RECOGNIZE_FAMILY("가족을 알아보지 못함"),
    GETTING_LOST("길을 잃거나 자주 가던 곳을 헤맴"),
    DELUSIONS("사람을 의심하는 망상"),
    CHILDISH_BEHAVIOR("어린아이 같은 행동"),
    AGGRESSIVE_BEHAVIOR("때리거나 욕설 등 공격적인 행동"),
    OTHERS("기타 증상");

    private final String description;

    public static DementiaSymptom fromDescription(String description) {
        return Arrays.stream(DementiaSymptom.values())
                .filter(symptom -> symptom.getDescription().equals(description))
                .findFirst()
                .orElse(null); // ❗ null 반환
    }
}
