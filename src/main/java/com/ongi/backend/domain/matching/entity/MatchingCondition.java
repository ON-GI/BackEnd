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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Embedded
    private MatchingCareRegion matchingCareRegion;

    private LocalDate careStartDate;

    private LocalDate careEndDate;

    private String benefits;

    @Column(nullable = false)
    private Integer minPayAmount;

    @Column(nullable = false)
    private Integer maxPayAmount;

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
