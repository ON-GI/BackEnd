package com.ongi.backend.domain.senior.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.dto.request.SeniorCareConditionRequestDto;
import com.ongi.backend.domain.senior.dto.request.SeniorDiseaseRequestDto;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.dto.response.SeniorResponseDto;
import com.ongi.backend.domain.senior.entity.*;
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

    @Transactional
    public void registerSenior(SeniorRequestDto request, Center center) {

        // 어르신 엔티티 생성 후 저장
        Senior senior = Senior.from(request, center);
        seniorRepository.save(senior);

        // 캐어 조건 저장 (SeniorCareConditionService 호출)
        updateCareCondition(request.careCondition(), senior);

        // 질병 정보 저장 (SeniorDiseaseService 호출)
        updateDisease(request.diseaseCondition(), senior);
    }

    @Transactional
    public void updateSenior(Long seniorId, SeniorRequestDto request, Center center) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ApplicationException(SeniorErrorCase.SENIOR_NOT_FOUND));

        senior.updateBasicInfo(request);
        updateCareCondition(request.careCondition(), senior);
        updateDisease(request.diseaseCondition(), senior);
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

    @Transactional
    public void deleteSenior(Long seniorId) {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new ApplicationException(SeniorErrorCase.SENIOR_NOT_FOUND));

        seniorRepository.delete(senior); // Soft Delete 적용으로 deleted_at이 자동 업데이트됨.
    }

    private void updateCareCondition(SeniorCareConditionRequestDto request, Senior senior) {
        SeniorCareCondition careCondition = seniorCareConditionRepository.findBySenior(senior)
                .orElseGet(() -> SeniorCareCondition.from(request, senior));

        careCondition.updateCareCondition(request);

        seniorCareConditionRepository.save(careCondition);
    }

    private void updateDisease(SeniorDiseaseRequestDto request, Senior senior) {
        SeniorDisease seniorDisease = seniorDiseaseRepository.findBySenior(senior)
                .orElseGet(() -> SeniorDisease.from(request, senior));

        seniorDisease.updateDisease(request);

        seniorDiseaseRepository.save(seniorDisease);
    }
}
