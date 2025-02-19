package com.ongi.backend.domain.matching.dto.request;

import com.ongi.backend.domain.matching.entity.MatchingCareRegion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatchingConditionRequestDto(

    @Valid
    @NotNull(message = "어르신 거주지를 입력하세요.")
    CareRegionDto careRegion,

    @NotNull(message = "근무 시작 날짜는 필수입니다.")
    LocalDate careStartDate,

    @NotNull(message = "근무 종료 날짜는 필수입니다.")
    LocalDate careEndDate,

    String benefits, // 복리후생

    @NotNull(message = "최소 시급은 필수입니다.")
    Integer minPayAmount,

    @NotNull(message = "최대 시급은 필수입니다.")
    Integer maxPayAmount
) {
    public record CareRegionDto(
            @NotBlank(message = "시/도를 입력하세요.") String city,
            @NotBlank(message = "구/군을 입력하세요.") String district,
            @NotBlank(message = "동/읍/면을 입력하세요.") String town
    ) {
        public MatchingCareRegion toEntity() {
            return new MatchingCareRegion(city, district, town);
        }
    }
}
