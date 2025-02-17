package com.ongi.backend.domain.senior.dto.response;

import com.ongi.backend.domain.senior.entity.DiseaseDementiaMapping;
import com.ongi.backend.domain.senior.entity.SeniorDisease;
import com.ongi.backend.domain.senior.entity.enums.DementiaSymptom;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeniorDiseaseResponseDto {

    private String disease;

    private String additionalDementiaSymptoms;

    private List<DementiaSymptom> dementiaSymptoms;

    public static SeniorDiseaseResponseDto from(SeniorDisease seniorDisease) {
        return SeniorDiseaseResponseDto.builder()
                .disease(seniorDisease.getDisease())
                .additionalDementiaSymptoms(seniorDisease.getAdditionalDementiaSymptoms())
                .dementiaSymptoms(seniorDisease.getDementiaMappings().stream()
                        .map(DiseaseDementiaMapping::getDementiaSymptom) // DementiaSymptom 리스트로 변환
                        .toList())
                .build();
    }
}
