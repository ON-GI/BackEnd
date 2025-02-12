package com.ongi.backend.entity.caregiver;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class WorkRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_condition_id", nullable = false)
    private CaregiverWorkCondition workCondition;  //  근무 조건과 다대일 (N:1)

    @Column(nullable = false)
    private String city;  // 시

    private String district;  // 구/군

    private String town;  // 동
}