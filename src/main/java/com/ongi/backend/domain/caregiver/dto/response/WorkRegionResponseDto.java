package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkRegion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkRegionResponseDto {

    private String city;
    private String district;
    private String town;

    @Builder(access= AccessLevel.PRIVATE)
    public WorkRegionResponseDto(String city, String district, String town) {
        this.city = city != null ? city : "전체";
        this.district = district != null ? district : "전체";
        this.town = town != null ? town : "전체";
    }

    public static WorkRegionResponseDto fromEntity(CaregiverWorkRegion caregiverWorkRegion) {
        return WorkRegionResponseDto.builder()
                .city(caregiverWorkRegion.getCity())
                .district(caregiverWorkRegion.getDistrict())
                .town(caregiverWorkRegion.getTown())
                .build();
    }
}