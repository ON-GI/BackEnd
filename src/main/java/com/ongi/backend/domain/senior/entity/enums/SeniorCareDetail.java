package com.ongi.backend.domain.senior.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeniorCareDetail {
    // 배변보조
    TOILETING_INDEPENDENT("스스로 배변 가능", SeniorCareType.TOILETING),
    TOILETING_ASSISTANCE("가끔 대소변 실수 시 도움", SeniorCareType.TOILETING),
    TOILETING_BED_CARE("기저귀 케어 필요", SeniorCareType.TOILETING),
    TOILETING_DEVICE("유치도뇨/방광루/장루", SeniorCareType.TOILETING),

    // 식사보조
    FEEDING_SELF("스스로 식사 가능", SeniorCareType.FEEDING),
    FEEDING_ASSIST("식사 차려드리기", SeniorCareType.FEEDING),
    FEEDING_COOKING("죽, 반찬 등 요리 필요", SeniorCareType.FEEDING),
    FEEDING_TUBE("경관식 보조", SeniorCareType.FEEDING),

    // 이동보조
    MOBILITY_SELF("스스로 거동 가능", SeniorCareType.MOBILITY),
    MOBILITY_ASSIST("이동시 부족 도움", SeniorCareType.MOBILITY),
    MOBILITY_WHEELCHAIR("휠체어 이동 보조", SeniorCareType.MOBILITY),
    MOBILITY_IMMOBILE("거동 불가", SeniorCareType.MOBILITY),

    // 일상생활 지원
    DAILY_CLEANING("청소, 빨래 보조", SeniorCareType.DAILY_LIVING),
    DAILY_BATHING("목욕 보조", SeniorCareType.DAILY_LIVING),
    DAILY_HOSPITAL("병원 동행", SeniorCareType.DAILY_LIVING),
    DAILY_EXERCISE("산책, 간단한 운동", SeniorCareType.DAILY_LIVING),
    DAILY_EMOTIONAL_SUPPORT("말벗 등 정서 지원", SeniorCareType.DAILY_LIVING),
    DAILY_COGNITIVE("인지 자극 활동", SeniorCareType.DAILY_LIVING),

    NOT_APPLICABLE("해당 없음", SeniorCareType.NOT_APPLICABLE);

    private final String description;
    private final SeniorCareType type;
}
