package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LicenseResponseDto(
        String licenseName,
        String licenseNumber,
        String licenseGrade
) {

    public static LicenseResponseDto from(CaregiverLicense caregiverLicense) {
        return LicenseResponseDto.builder()
                .licenseName(caregiverLicense.getLicenseName().toString())
                .licenseNumber(caregiverLicense.getLicenseNumber())
                .licenseGrade(caregiverLicense.getLicenseGrade())
                .build();
    }

}
