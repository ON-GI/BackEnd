package com.ongi.backend.entity.Caregiver;

import jakarta.persistence.*;
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
}
