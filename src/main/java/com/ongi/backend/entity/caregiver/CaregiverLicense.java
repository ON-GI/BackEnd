package com.ongi.backend.entity.caregiver;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public CaregiverLicense(Caregiver caregiver, String licenseName, String licenseNumber, String licenseGrade) {
        this.caregiver = caregiver;
        this.licenseName = licenseName;
        this.licenseNumber = licenseNumber;
        this.licenseGrade = licenseGrade;
    }
}
