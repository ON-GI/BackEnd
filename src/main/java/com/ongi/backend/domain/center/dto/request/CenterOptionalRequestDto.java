package com.ongi.backend.domain.center.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CenterOptionalRequestDto(
        String centerGrade,
        LocalDate establishmentDate,
        String description,

        @NotNull(message = "차량 소유 여부를 작성하세요.")
        boolean hasVehicle
) {
}
