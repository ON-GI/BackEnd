package com.ongi.backend.domain.matching.entity;

import com.ongi.backend.domain.matching.dto.request.MatchingConditionRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingCondition {

    @Column
    @Embedded
    private MatchingCareRegion matchingCareRegion;

    private LocalDate careStartDate;    // 캐어 시작 일시

    private LocalDate careEndDate;      // 캐어 종료 일시

    private String benefits;        // 복리후생

    @Column(nullable = false)
    private Integer minPayAmount;   // 시급 하한선

    @Column(nullable = false)
    private Integer maxPayAmount;   // 시급 상한선

    public static MatchingCondition from(MatchingConditionRequestDto requestDto) {
        return MatchingCondition.builder()
                .matchingCareRegion(requestDto.careRegion().toEntity())
                .careStartDate(requestDto.careStartDate())
                .careEndDate(requestDto.careEndDate())
                .benefits(requestDto.benefits())
                .minPayAmount(requestDto.minPayAmount())
                .maxPayAmount(requestDto.maxPayAmount())
                .build();
    }
}
