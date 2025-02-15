package com.ongi.backend.domain.caregiver.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.service.FileUploadService;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.LicenseRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.ValidateIdRequestDto;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import com.ongi.backend.domain.caregiver.exception.CaregiverNotFoundException;
import com.ongi.backend.domain.caregiver.repository.CaregiverLicenseRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final FileUploadService fileUploadService;
    private final PasswordEncoder passwordEncoder;
    
    public void validateId(ValidateIdRequestDto requestDto) {
        if(existsDuplicateId(requestDto.getLoginId())) {
            throw new ApplicationException(CaregiverErrorCase.DUPLICATE_LOGIN_ID);
        }
    }

    @Transactional
    public Long registerCaregiver(CaregiverRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.password());
        Caregiver caregiver = saveCaregiver(requestDto, encodedPassword);
        return caregiver.getId();
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

    public Caregiver findByLoginId(String loginId) {
        return caregiverRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.CAREGIVER_NOT_FOUND));
    }

    private boolean existsDuplicateId(String loginId) {
        return caregiverRepository.existsByLoginId(loginId);
    }

    private Caregiver saveCaregiver(CaregiverRequestDto requestDto, String encodedPassword) {
        Caregiver caregiver = caregiverRepository.save(Caregiver.from(requestDto, encodedPassword));
        List<CaregiverLicense> licenses = saveCaregiverLicenses(requestDto.licenses(), caregiver);
        caregiver.setLicenses(licenses);
        return caregiver;
    }

    private List<CaregiverLicense> saveCaregiverLicenses(List<LicenseRequestDto> licenseDtos, Caregiver caregiver) {
        List<CaregiverLicense> licenses = licenseDtos.stream()
                .map(dto -> CaregiverLicense.from(dto, caregiver))
                .collect(Collectors.toList());

        caregiverLicenseRepository.saveAll(licenses);

        return licenses;
    }
}
