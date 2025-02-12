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

    private final CaregiverWorkService caregiverWorkService;

    @Transactional
    public Long registerCaregiver(CaregiverRequestDto requestDto) {
        // Caregiver 저장
        Caregiver caregiver = saveCaregiver(requestDto);

        // CaregiverLicense 저장
        saveCaregiverLicenses(requestDto.getLicenses(), caregiver);

        caregiverWorkService.registerWorkCondition(requestDto.getWorkCondition(), caregiver);

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
}
