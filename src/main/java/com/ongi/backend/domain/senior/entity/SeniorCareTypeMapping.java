package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
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
@Table(name = "senior_care_type_mapping")
public class SeniorCareTypeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_care_condition_id", nullable = false)
    private SeniorCareCondition seniorCareCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeniorCareDetail seniorCareDetail;
}
