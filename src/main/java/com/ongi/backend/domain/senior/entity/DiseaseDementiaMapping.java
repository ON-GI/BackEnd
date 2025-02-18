package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.domain.senior.entity.enums.DementiaSymptom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "disease_dementia_mapping")
public class DiseaseDementiaMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_disease_id", nullable = false)
    private SeniorDisease seniorDisease;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DementiaSymptom dementiaSymptom;
}
