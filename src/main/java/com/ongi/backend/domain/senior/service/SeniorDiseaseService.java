package com.ongi.backend.domain.senior.service;

import com.ongi.backend.domain.senior.dto.request.SeniorDiseaseRequestDto;
import com.ongi.backend.domain.senior.entity.DiseaseDementiaMapping;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.SeniorDisease;
import com.ongi.backend.domain.senior.entity.enums.DementiaSymptom;
import com.ongi.backend.domain.senior.repository.DiseaseDementiaMappingRepository;
import com.ongi.backend.domain.senior.repository.SeniorDiseaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeniorDiseaseService {

    private final SeniorDiseaseRepository seniorDiseaseRepository;
    private final DiseaseDementiaMappingRepository diseaseDementiaMappingRepository;

    @Transactional
    public SeniorDisease saveDisease(SeniorDiseaseRequestDto request, Senior senior) {
        SeniorDisease seniorDisease = SeniorDisease.from(request, senior);
        seniorDiseaseRepository.save(seniorDisease);

        // ✅ 치매 증상 매핑 저장
        for (String symptom : request.dementiaSymptoms()) {
            DiseaseDementiaMapping mapping = DiseaseDementiaMapping.builder()
                    .seniorDisease(seniorDisease)
                    .dementiaSymptom(DementiaSymptom.valueOf(symptom))
                    .build();

            diseaseDementiaMappingRepository.save(mapping);
        }

        return seniorDisease;
    }
}
