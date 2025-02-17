package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.domain.senior.dto.request.SeniorDiseaseRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorDisease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    @Column
    private String disease;

    @Column
    private String additionalDementiaSymptoms; // 사용자가 직접 입력한 기타 치매 증상

    // ✅ SeniorDisease - DiseaseDementiaMapping (1:N 관계)
    @OneToMany(mappedBy = "seniorDisease", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiseaseDementiaMapping> dementiaMappings = new ArrayList<>();

    public static SeniorDisease from(SeniorDiseaseRequestDto requestDto, Senior senior) {
        return SeniorDisease.builder()
                .senior(senior)
                .disease(requestDto.disease())
                .additionalDementiaSymptoms(requestDto.additionalDementiaSymptoms())
                .build();
    }
}
