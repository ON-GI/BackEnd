package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.WorkRegionRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CaregiverWorkRegion {

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

    public static CaregiverWorkRegion from(WorkRegionRequestDto workRegionRequestDto, CaregiverWorkCondition workCondition) {
        return CaregiverWorkRegion.builder()
                .workCondition(workCondition)
                .city(workRegionRequestDto.getCity())
                .district(workRegionRequestDto.getDistrict())
                .town(workRegionRequestDto.getTown())
                .build();
    }

}