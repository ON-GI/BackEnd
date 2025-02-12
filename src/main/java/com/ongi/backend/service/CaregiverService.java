package com.ongi.backend.service;

import com.ongi.backend.dto.caregiver.*;
import com.ongi.backend.entity.caregiver.*;
import com.ongi.backend.repository.caregiver.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;

    private final CaregiverLicenseRepository caregiverLicenseRepository;

    private final CaregiverWorkConditionRepository caregiverWorkConditionRepository;

    private final WorkRegionRepository workRegionRepository;

    private final WorkTimeRepository workTimeRepository;

    @Transactional
    public Long registerCaregiver(CaregiverRequestDto requestDto) {
        // Caregiver 저장
        Caregiver caregiver = saveCaregiver(requestDto);

        // CaregiverLicense 저장
        saveCaregiverLicenses(requestDto.getLicenses(), caregiver);

        // CaregiverWorkCondition 저장 (WorkRegion, WorkTime 포함)
        saveCaregiverWorkCondition(requestDto.getWorkCondition(), caregiver);

        return caregiver.getId();
    }

    /**
     * 요양보호사(Caregiver) 저장
     */
    private Caregiver saveCaregiver(CaregiverRequestDto requestDto) {
        return caregiverRepository.save(requestDto.toEntity());
    }

    /**
     * 요양보호사 자격증(CaregiverLicense) 저장
     */
    private void saveCaregiverLicenses(List<LicenseRequestDto> licenseDtos, Caregiver caregiver) {
        if (licenseDtos == null || licenseDtos.isEmpty()) return;

        List<CaregiverLicense> licenses = licenseDtos.stream()
                .map(dto -> dto.toEntity(caregiver))
                .collect(Collectors.toList());

        caregiverLicenseRepository.saveAll(licenses);
    }

    /**
     * 근무 조건(CaregiverWorkCondition) 저장 및 관련 정보 저장
     */
    private void saveCaregiverWorkCondition(WorkConditionRequestDto workConditionDto, Caregiver caregiver) {
        if (workConditionDto == null) return;

        // CaregiverWorkCondition 저장
        CaregiverWorkCondition workCondition = workConditionDto.toEntity(caregiver);
        caregiverWorkConditionRepository.save(workCondition);

        // WorkRegion 저장
        saveWorkRegions(workConditionDto.getWorkRegions(), workCondition);

        // WorkTime 저장
        saveWorkTimes(workConditionDto.getWorkTimes(), workCondition);
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
