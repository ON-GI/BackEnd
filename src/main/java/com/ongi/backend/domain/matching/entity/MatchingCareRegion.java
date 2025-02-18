package com.ongi.backend.domain.matching.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingCareRegion {

    private String city;      // 시/도 (서울특별시, 경기도 등)
    private String district;  // 구/군 (강남구, 수원시 등)
    private String town;      // 동/읍/면 (역삼동, 장안읍 등)

    public MatchingCareRegion(String city, String district, String town) {
        this.city = city;
        this.district = district;
        this.town = town;
    }

    @Override
    public String toString() {
        return city + " " + district + " " + town;
    }
}
