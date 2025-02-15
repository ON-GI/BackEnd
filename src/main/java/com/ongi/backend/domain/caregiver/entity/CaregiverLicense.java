package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.LicenseRequestDto;
import com.ongi.backend.domain.caregiver.entity.enums.LicenseType;
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

    @Column(nullable = false)
    private LicenseType licenseName; // 자격증 이름

    @Column(nullable = false)
    private String licenseNumber; // 자격증 번호

    private String licenseGrade; // 자격증 등급

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;

    public static CaregiverLicense from(LicenseRequestDto request, Caregiver caregiver) {
        return CaregiverLicense.builder()
                .caregiver(caregiver)
                .licenseName(LicenseType.fromString(request.licenseName()))
                .licenseNumber(request.licenseNumber())
                .licenseGrade(request.licenseGrade())
                .build();
    }

}
