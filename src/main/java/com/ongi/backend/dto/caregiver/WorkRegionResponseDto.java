package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.WorkRegion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkRegionResponseDto {

    private String city;
    private String district;
    private String town;

    @Builder
    public WorkRegionResponseDto(String city, String district, String town) {
        this.city = city != null ? city : "전체";
        this.district = district != null ? district : "전체";
        this.town = town != null ? town : "전체";
    }

    public static WorkRegionResponseDto fromEntity(WorkRegion workRegion) {
        return WorkRegionResponseDto.builder()
                .city(workRegion.getCity())
                .district(workRegion.getDistrict())
                .town(workRegion.getTown())
                .build();
    }
}