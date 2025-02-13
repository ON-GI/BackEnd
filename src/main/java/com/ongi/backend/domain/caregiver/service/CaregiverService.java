package com.ongi.backend.domain.caregiver.service;

import com.ongi.backend.common.service.FileUploadService;
import com.ongi.backend.domain.caregiver.exception.CaregiverNotFoundException;
import com.ongi.backend.domain.caregiver.repository.CaregiverLicenseRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverRepository;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.LicenseRequestDto;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private final FileUploadService fileUploadService;

    @Transactional
    public Long registerCaregiver(CaregiverRequestDto requestDto) {
        // Caregiver 저장
        Caregiver caregiver = saveCaregiver(requestDto);

        // CaregiverLicense 저장
        saveCaregiverLicenses(requestDto.getLicenses(), caregiver);

        return caregiver.getId();
    }

    /**
     * 요양보호사(Caregiver) 저장
     */
    private Caregiver saveCaregiver(CaregiverRequestDto requestDto) {
        return caregiverRepository.save(Caregiver.from(requestDto));
    }

    /**
     * 요양보호사 자격증(CaregiverLicense) 저장
     */
    private void saveCaregiverLicenses(List<LicenseRequestDto> licenseDtos, Caregiver caregiver) {
        if (licenseDtos == null || licenseDtos.isEmpty()) return;

        List<CaregiverLicense> licenses = licenseDtos.stream()
                .map(dto -> CaregiverLicense.from(dto, caregiver))
                .collect(Collectors.toList());

        caregiverLicenseRepository.saveAll(licenses);
    }

    @Transactional
    public void updateCaregiverProfileImage(MultipartFile profileImage, Long caregiverId) {
        Caregiver caregiver = caregiverRepository.findById(caregiverId)
                .orElseThrow(CaregiverNotFoundException::new);

        String oldProfileImageUrl = caregiver.getProfileImageUrl();
        if (oldProfileImageUrl != null) {
            fileUploadService.deleteImage(oldProfileImageUrl);
        }

        String imageUrl = fileUploadService.uploadFileToS3(profileImage);
        caregiver.updateProfileImageUrl(imageUrl);
    }
}
