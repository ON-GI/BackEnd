package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor
public class LicenseResponseDto {

    private String licenseName;
    private String licenseNumber;
    private String licenseGrade;

    @Builder(access= AccessLevel.PRIVATE)
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
