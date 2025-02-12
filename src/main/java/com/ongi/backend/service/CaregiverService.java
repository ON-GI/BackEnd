package com.ongi.backend.service;

import com.ongi.backend.dto.caregiver.CaregiverLicenseDto;
import com.ongi.backend.dto.caregiver.CaregiverRequestDto;
import com.ongi.backend.dto.caregiver.CaregiverWorkConditionDto;
import com.ongi.backend.dto.caregiver.WorkRegionDto;
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

        // 2️⃣ Caregiver 엔티티 저장
        Caregiver caregiver = caregiverRepository.save(requestDto.toEntity());

        // 6️⃣ CaregiverLicense 저장
        List<CaregiverLicense> licenses = requestDto.getLicenses().stream()
                .map(licenseDto -> licenseDto.toEntity(caregiver))
                .collect(Collectors.toList());
        caregiverLicenseRepository.saveAll(licenses);

        // 3️⃣ CaregiverWorkCondition 저장
        CaregiverWorkCondition workCondition = requestDto.getWorkCondition().toEntity(caregiver);
        caregiverWorkConditionRepository.save(workCondition);

        // WorkRegion 엔티티 리스트 생성
        List<WorkRegion> regions = requestDto.getWorkCondition().getWorkRegions().stream()
                .map(workRegionDto -> workRegionDto.toEntity(workCondition))
                .collect(Collectors.toList());

        workRegionRepository.saveAll(regions);

        // WorkTime 엔티티 리스트 생성
        List<WorkTime> times = requestDto.getWorkCondition().getWorkTimes().stream()
                .map(workTimeDto -> workTimeDto.toEntity(workCondition))
                .collect(Collectors.toList());

        workTimeRepository.saveAll(times);

        return caregiver.getId();
    }
}
