package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import com.ongi.backend.entity.caregiver.WorkRegion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkRegionRequestDto {

    private String city;
    private String district;
    private String town;

    @Builder
    public WorkRegionRequestDto(String city, String district, String town) {
        this.city = city;
        this.district = district;
        this.town = town;
    }

    public WorkRegion toEntity(CaregiverWorkCondition workCondition) {
        return WorkRegion.builder()
                .workCondition(workCondition)
                .city(this.city)
                .district(this.district)
                .town(this.town)
                .build();
    }
}