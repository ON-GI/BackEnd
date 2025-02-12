package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.LicenseRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CaregiverLicense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;

    @Column(nullable = false)
    private String licenseName; // 자격증 이름

    @Column(nullable = false)
    private String licenseNumber; // 자격증 번호

    private String licenseGrade; // 자격증 등급

    public static CaregiverLicense from(LicenseRequestDto licenseRequestDto, Caregiver caregiver) {
        return CaregiverLicense.builder()
                .caregiver(caregiver)
                .licenseName(licenseRequestDto.getLicenseName())
                .licenseNumber(licenseRequestDto.getLicenseNumber())
                .licenseGrade(licenseRequestDto.getLicenseGrade())
                .build();
    }

}
