package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.domain.senior.dto.request.SeniorMatchingConditionRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorMatchingCondition {

    private LocalDate careStartDate;    // 캐어 시작 일시

    private LocalDate careEndDate;      // 캐어 종료 일시

    private String benefits;        // 복리후생

    @Column(nullable = false)
    private Integer minPayAmount;   // 시급 하한선

    @Column(nullable = false)
    private Integer maxPayAmount;   // 시급 상한선

    public static SeniorMatchingCondition from(SeniorMatchingConditionRequestDto requestDto) {
        return SeniorMatchingCondition.builder()
                .careStartDate(requestDto.careStartDate())
                .careEndDate(requestDto.careEndDate())
                .benefits(requestDto.benefits())
                .minPayAmount(requestDto.minPayAmount())
                .maxPayAmount(requestDto.maxPayAmount())
                .build();
    }
}
