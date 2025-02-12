package com.ongi.backend.domain.caregiver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class LicenseRequestDto {

    private String licenseName;
    private String licenseNumber;
    private String licenseGrade;

    @Builder
    public LicenseRequestDto(String licenseName, String licenseNumber, String licenseGrade) {
        this.licenseName = licenseName;
        this.licenseNumber = licenseNumber;
        this.licenseGrade = licenseGrade;
    }
}
