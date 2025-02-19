package com.ongi.backend.domain.center.dto.response;

import com.ongi.backend.domain.center.entity.Center;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
public record CenterOptionalResponseDto(
        String centerGrade,
        LocalDate establishmentDate,
        String description,
        boolean hasVehicle
) {

    public static CenterOptionalResponseDto from(Center center) {
        return CenterOptionalResponseDto.builder()
                .centerGrade(center.getCenterGrade() == null ? null : center.getCenterGrade().name())
                .establishmentDate(center.getEstablishmentDate())
                .description(center.getDescription())
                .hasVehicle(center.getHasVehicle())
                .build();
    }

}
