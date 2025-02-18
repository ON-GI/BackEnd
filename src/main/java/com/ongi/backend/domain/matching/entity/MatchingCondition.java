package com.ongi.backend.domain.matching.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
