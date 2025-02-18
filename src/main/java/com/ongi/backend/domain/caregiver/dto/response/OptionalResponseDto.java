package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverOptional;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record OptionalResponseDto(
        String career,
        String description
) {
    public static OptionalResponseDto from(CaregiverOptional optional) {
        return OptionalResponseDto.builder()
                .career(optional.getCareer().toString())
                .description(optional.getDescription())
                .build();
    }
}
