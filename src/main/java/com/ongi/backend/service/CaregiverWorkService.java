package com.ongi.backend.service;

import com.ongi.backend.dto.caregiver.*;
import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import com.ongi.backend.entity.caregiver.WorkRegion;
import com.ongi.backend.entity.caregiver.WorkTime;
import com.ongi.backend.repository.caregiver.CaregiverWorkConditionRepository;
import com.ongi.backend.repository.caregiver.WorkRegionRepository;
import com.ongi.backend.repository.caregiver.WorkTimeRepository;
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

    private final WorkRegionRepository workRegionRepository;

    private final WorkTimeRepository workTimeRepository;

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
        CaregiverWorkCondition workCondition = workConditionRequestDto.toEntity(caregiver);
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
        workRegionRepository.deleteByWorkCondition(workCondition);
        workTimeRepository.deleteByWorkCondition(workCondition);

        saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);
        saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
    }

    /**
     * 근무 가능 지역(WorkRegion) 저장
     */
    private void saveWorkRegions(List<WorkRegionRequestDto> workRegionRequestDto, CaregiverWorkCondition workCondition) {
        if (workRegionRequestDto == null || workRegionRequestDto.isEmpty()) return;

        List<WorkRegion> regions = workRegionRequestDto.stream()
                .map(dto -> dto.toEntity(workCondition))
                .collect(Collectors.toList());

        workRegionRepository.saveAll(regions);
    }

    /**
     * 근무 가능 시간(WorkTime) 저장
     */
    private void saveWorkTimes(List<WorkTimeRequestDto> workTimeRequestDtos, CaregiverWorkCondition workCondition) {
        if (workTimeRequestDtos == null || workTimeRequestDtos.isEmpty()) return;

        List<WorkTime> times = workTimeRequestDtos.stream()
                .map(dto -> dto.toEntity(workCondition))
                .collect(Collectors.toList());

        workTimeRepository.saveAll(times);
    }
}
