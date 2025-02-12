package com.ongi.backend.dto.caregiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WorkRegionDto {

    private String city;
    private String district;
    private String town;

    @Builder
    public WorkRegionDto(String city, String district, String town) {
        this.city = city;
        this.district = district;
        this.town = town;
    }
}