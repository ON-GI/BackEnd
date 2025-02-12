package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverLicense;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class LicenseResponseDto {

    private String licenseName;
    private String licenseNumber;
    private String licenseGrade;

    @Builder
    public LicenseResponseDto(String licenseName, String licenseNumber, String licenseGrade) {
        this.licenseName = licenseName;
        this.licenseNumber = licenseNumber;
        this.licenseGrade = licenseGrade;
    }

    public static LicenseResponseDto fromEntity(CaregiverLicense license) {
        return LicenseResponseDto.builder()
                .licenseName(license.getLicenseName())
                .licenseNumber(license.getLicenseNumber())
                .licenseGrade(license.getLicenseGrade())
                .build();
    }
}
