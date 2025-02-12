package com.ongi.backend.service;

import com.ongi.backend.dto.caregiver.WorkConditionRequestDto;
import com.ongi.backend.dto.caregiver.WorkRegionRequestDto;
import com.ongi.backend.dto.caregiver.WorkTimeRequestDto;
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

    @Transactional
    public void registerWork(WorkConditionRequestDto workConditionRequestDto, Caregiver caregiver) {
        if (workConditionRequestDto == null) return;

        // CaregiverWorkCondition 저장
        CaregiverWorkCondition workCondition = workConditionRequestDto.toEntity(caregiver);
        caregiverWorkConditionRepository.save(workCondition);

        // WorkRegion 저장
        saveWorkRegions(workConditionRequestDto.getWorkRegions(), workCondition);

        // WorkTime 저장
        saveWorkTimes(workConditionRequestDto.getWorkTimes(), workCondition);
    }

    /**
     * 근무 가능 지역(WorkRegion) 저장
     */
    private void saveWorkRegions(List<WorkRegionRequestDto> workRegionRequestDtos, CaregiverWorkCondition workCondition) {
        if (workRegionRequestDtos == null || workRegionRequestDtos.isEmpty()) return;

        List<WorkRegion> regions = workRegionRequestDtos.stream()
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
