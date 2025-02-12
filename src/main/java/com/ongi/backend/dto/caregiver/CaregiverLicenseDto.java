package com.ongi.backend.dto.caregiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class CaregiverLicenseDto {

    private String licenseName;
    private String licenseNumber;
    private String licenseGrade;

    @Builder
    public CaregiverLicenseDto(String licenseName, String licenseNumber, String licenseGrade) {
        this.licenseName = licenseName;
        this.licenseNumber = licenseNumber;
        this.licenseGrade = licenseGrade;
    }
}
