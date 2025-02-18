package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.OptionalRequestDto;
import com.ongi.backend.domain.caregiver.entity.enums.CaregiverCareer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CaregiverOptional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CaregiverCareer career;  // 경력 기간
    private String description;     // 한 줄 소개

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;

    public static CaregiverOptional from(OptionalRequestDto request, Caregiver caregiver) {
        return CaregiverOptional.builder()
                .career(CaregiverCareer.fromString(request.career()))
                .description(request.description())
                .caregiver(caregiver)
                .build();
    }

    public void updateFrom(@Valid OptionalRequestDto request) {
        this.career = CaregiverCareer.fromString(request.career());
        this.description = request.description();
    }
}
