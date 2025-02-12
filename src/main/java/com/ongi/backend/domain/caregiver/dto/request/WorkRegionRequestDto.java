package com.ongi.backend.domain.caregiver.dto.request;

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
}