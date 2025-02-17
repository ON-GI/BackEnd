package com.ongi.backend.domain.senior.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.dto.request.SeniorCareConditionRequestDto;
import com.ongi.backend.domain.senior.dto.request.SeniorDiseaseRequestDto;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.dto.response.SeniorResponseDto;
import com.ongi.backend.domain.senior.entity.*;
import com.ongi.backend.domain.senior.entity.enums.DementiaSymptom;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import com.ongi.backend.domain.senior.exception.SeniorErrorCase;
import com.ongi.backend.domain.senior.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SeniorService {

    private final SeniorRepository seniorRepository;
    private final SeniorCareConditionRepository seniorCareConditionRepository;

    private final SeniorDiseaseRepository seniorDiseaseRepository;

    private final DiseaseDementiaMappingRepository diseaseDementiaMappingRepository;


    @Transactional
    public Senior registerSenior(SeniorRequestDto request, Center center) {

        // 어르신 엔티티 생성 후 저장
        Senior senior = Senior.from(request, center);
        seniorRepository.save(senior);

        // 캐어 조건 저장 (SeniorCareConditionService 호출)
        saveCareCondition(request.careCondition(), senior);

        // 질병 정보 저장 (SeniorDiseaseService 호출)
        saveDisease(request.diseaseCondition(), senior);

        return senior;
    }

    @Transactional
    public SeniorResponseDto findSenior(Long seniorId) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ApplicationException(SeniorErrorCase.SENIOR_NOT_FOUND));

        return SeniorResponseDto.fromEntity(senior);
    }

    @Transactional
    public List<SeniorResponseDto> findSeniorsByCenter(Center center) {
        List<Senior> seniors = seniorRepository.findSeniorsByCenter(center);

        if (seniors.isEmpty()) {
            return List.of(); // 빈 리스트 반환
        }

        return seniors.stream()
                .map(SeniorResponseDto::fromEntity)
                .toList();
    }

    private void saveCareCondition(SeniorCareConditionRequestDto request, Senior senior) {
        SeniorCareCondition careCondition = SeniorCareCondition.from(request, senior);

        // careTypes와 careTimes를 한 번에 추가하여 Cascade 처리
        careCondition.getCareTypes().addAll(
                request.careDetails().stream()
                        .map(detail -> SeniorCareTypeMapping.builder()
                                .seniorCareCondition(careCondition)
                                .seniorCareDetail(SeniorCareDetail.valueOf(detail))
                                .build())
                        .toList()
        );

        careCondition.getSeniorCareTimes().addAll(
                request.careTimes().stream()
                        .map(time -> SeniorCareTime.from(time, careCondition))
                        .toList()
        );

        // ✅ `cascade = CascadeType.ALL` 설정을 통해 한 번의 save로 모두 저장됨
        seniorCareConditionRepository.save(careCondition);
    }

    /**
     * ✅ SeniorDisease 생성 및 저장 (원래 `SeniorDiseaseService`의 기능)
     */
    private void saveDisease(SeniorDiseaseRequestDto request, Senior senior) {
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

        // ✅ `cascade = CascadeType.ALL`을 활용하여 한 번의 save로 저장
        seniorDiseaseRepository.save(seniorDisease);
    }
}
