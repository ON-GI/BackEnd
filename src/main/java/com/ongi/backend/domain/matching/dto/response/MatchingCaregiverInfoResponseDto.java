package com.ongi.backend.domain.matching.dto.response;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record MatchingCaregiverInfoResponseDto(
        String name,
        List<LicenseResponseDto> licenses,
        WorkConditionResponseDto workCondition
) {

    public static MatchingCaregiverInfoResponseDto from(Caregiver caregiver) {
        List<LicenseResponseDto> licenses = caregiver.getCaregiverInformation().getLicenses().stream()
                .map(LicenseResponseDto::from)
                .toList();

        return MatchingCaregiverInfoResponseDto.builder()
                .name(caregiver.getName())
                .licenses(licenses)
                .workCondition(WorkConditionResponseDto.from(caregiver.getWorkCondition()))
                .build();
    }
}
