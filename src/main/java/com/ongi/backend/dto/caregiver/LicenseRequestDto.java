package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.entity.caregiver.CaregiverLicense;
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

    public CaregiverLicense toEntity(Caregiver caregiver) {
        return CaregiverLicense.builder()
                .caregiver(caregiver)
                .licenseName(this.licenseName)
                .licenseNumber(this.licenseNumber)
                .licenseGrade(this.licenseGrade)
                .build();
    }
}
