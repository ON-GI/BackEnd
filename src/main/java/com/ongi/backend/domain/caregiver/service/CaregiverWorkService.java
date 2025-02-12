package com.ongi.backend.domain.caregiver.service;

import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkTimeRequestDto;
import com.ongi.backend.domain.caregiver.dto.response.WorkConditionResponseDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkRegionRequestDto;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkRegion;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkTime;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkConditionRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkRegionRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverWorkTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaregiverWorkService {

    private final CaregiverWorkConditionRepository caregiverWorkConditionRepository;

    private final CaregiverWorkRegionRepository caregiverWorkRegionRepository;

    private final CaregiverWorkTimeRepository caregiverWorkTimeRepository;

    @Transactional(readOnly = true)
    public WorkConditionResponseDto getWorkConditionByCaregiverId(Long caregiverId) {
        CaregiverWorkCondition workCondition = caregiverWorkConditionRepository.findByCaregiverId(caregiverId)
                .orElseThrow(() -> new IllegalArgumentException("근무 조건을 찾을 수 없습니다: caregiverId=" + caregiverId));

        return WorkConditionResponseDto.fromEntity(workCondition);
    }

    @Transactional
    public void registerWorkCondition(WorkConditionRequestDto workConditionRequestDto, Caregiver caregiver) {
        if (workConditionRequestDto == null) return;

        // CaregiverWorkCondition 저장
        CaregiverWorkCondition workCondition = CaregiverWorkCondition.from(workConditionRequestDto, caregiver);
        caregiverWorkConditionRepository.save(workCondition);

        // WorkRegion 저장
        saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);

        // WorkTime 저장
        saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
    }

    @Transactional
    public void updateWorkCondition(WorkConditionRequestDto workConditionRequestDto, Long caregiverId) {

        log.info(workConditionRequestDto.toString());

        CaregiverWorkCondition workCondition = caregiverWorkConditionRepository.findByCaregiverId(caregiverId)
                .orElseThrow(() -> new IllegalArgumentException("근무 조건을 수정할 수 없습니다: caregiverId=" + caregiverId));

        workCondition.updatePay(workConditionRequestDto.getMinHourPay(), workConditionRequestDto.getMaxHourPay());

        caregiverWorkConditionRepository.save(workCondition);

        // 기존 WorkRegion 및 WorkTime 삭제 후 새로 저장
        caregiverWorkRegionRepository.deleteByWorkCondition(workCondition);
        caregiverWorkTimeRepository.deleteByWorkCondition(workCondition);

        saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);
        saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
    }

    /**
     * 근무 가능 지역(WorkRegion) 저장
     */
    private void saveWorkRegions(List<WorkRegionRequestDto> workRegionRequestDto, CaregiverWorkCondition workCondition) {
        if (workRegionRequestDto == null || workRegionRequestDto.isEmpty()) return;

        List<CaregiverWorkRegion> regions = workRegionRequestDto.stream()
                .map(dto -> CaregiverWorkRegion.from(dto, workCondition))
                .collect(Collectors.toList());

        caregiverWorkRegionRepository.saveAll(regions);
    }

    /**
     * 근무 가능 시간(WorkTime) 저장
     */
    private void saveWorkTimes(List<WorkTimeRequestDto> workTimeRequestDtos, CaregiverWorkCondition workCondition) {
        if (workTimeRequestDtos == null || workTimeRequestDtos.isEmpty()) return;

        List<CaregiverWorkTime> times = workTimeRequestDtos.stream()
                .map(dto -> CaregiverWorkTime.from(dto, workCondition))
                .collect(Collectors.toList());

        caregiverWorkTimeRepository.saveAll(times);
    }
}
