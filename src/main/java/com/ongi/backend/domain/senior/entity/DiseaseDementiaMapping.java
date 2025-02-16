package com.ongi.backend.domain.senior.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DiseaseDementiaMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_disease_id", nullable = false)
    private SeniorDisease seniorDisease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dementia_symptom_id", nullable = false)
    private DementiaSymptom dementiaSymptom;

    @Builder
    public DiseaseDementiaMapping(SeniorDisease seniorDisease, DementiaSymptom dementiaSymptom) {
        this.seniorDisease = seniorDisease;
        this.dementiaSymptom = dementiaSymptom;
    }
}
