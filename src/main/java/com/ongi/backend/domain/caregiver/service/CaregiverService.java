package com.ongi.backend.domain.caregiver.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.service.FileUploadService;
import com.ongi.backend.domain.caregiver.dto.request.*;
import com.ongi.backend.domain.caregiver.dto.response.CaregiverResponseDto;
import com.ongi.backend.domain.caregiver.dto.response.CaregiverSignupResponse;
import com.ongi.backend.domain.caregiver.dto.response.InformationResponseDto;
import com.ongi.backend.domain.caregiver.dto.response.OptionalResponseDto;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverInformation;
import com.ongi.backend.domain.caregiver.entity.CaregiverLicense;
import com.ongi.backend.domain.caregiver.entity.CaregiverOptional;
import com.ongi.backend.domain.caregiver.exception.CaregiverErrorCase;
import com.ongi.backend.domain.caregiver.exception.CaregiverNotFoundException;
import com.ongi.backend.domain.caregiver.repository.CaregiverInformationRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverLicenseRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverOptionalRepository;
import com.ongi.backend.domain.caregiver.repository.CaregiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaregiverService {

    private final CaregiverRepository caregiverRepository;
    private final CaregiverInformationRepository caregiverInformationRepository;
    private final CaregiverOptionalRepository caregiverOptionalRepository;
    private final CaregiverLicenseRepository caregiverLicenseRepository;
    private final FileUploadService fileUploadService;
    private final PasswordEncoder passwordEncoder;
    
    public void validateId(ValidateIdRequestDto requestDto) {
        if(existsDuplicateId(requestDto.getLoginId())) {
            throw new ApplicationException(CaregiverErrorCase.DUPLICATE_LOGIN_ID);
        }
    }

    @Transactional
    public CaregiverSignupResponse registerCaregiver(CaregiverSignupRequestDto requestDto) {
        String encodedPassword = passwordEncoder.encode(requestDto.password());
        Caregiver caregiver = saveCaregiver(requestDto, encodedPassword);
        return new CaregiverSignupResponse(caregiver.getId());
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

    public CaregiverResponseDto getCaregiver(Long caregiverId) {
        Caregiver caregiver = findCaregiverById(caregiverId);
        return CaregiverResponseDto.from(caregiver);
    }

    @Transactional
    public void updateCaregiver(Long caregiverId, CaregiverUpdateRequestDto request) {
        Caregiver caregiver = findCaregiverById(caregiverId);
        caregiver.updateBy(request);
        caregiverRepository.save(caregiver);
    }

    public InformationResponseDto getCaregiverInfo(Long caregiverId) {
        Caregiver caregiver = findCaregiverByIdJoinInfo(caregiverId);
        return InformationResponseDto.from(caregiver.getCaregiverInformation());
    }

    @Transactional
    public void updateCaregiverInfo(Long caregiverId, InformationRequestDto request) {
        CaregiverInformation information = findCaregiverById(caregiverId).getCaregiverInformation();
        information.update(request);

        List<CaregiverLicense> licenses = request.licenses().stream()
                .map(license -> CaregiverLicense.from(license, information))
                .toList();

        information.updateLicenses(licenses);
        caregiverInformationRepository.save(information);
    }

    public OptionalResponseDto getCaregiverOptional(Long caregiverId) {
        Caregiver caregiver = findCaregiverById(caregiverId);
        return OptionalResponseDto.from(caregiver.getCaregiverOptional());
    }

    @Transactional
    public void updateCaregiverOptional(Long caregiverId, OptionalRequestDto request) {
        CaregiverOptional optional = findCaregiverById(caregiverId).getCaregiverOptional();
        optional.updateFrom(request);
        caregiverOptionalRepository.save(optional);
    }

    private boolean existsDuplicateId(String loginId) {
        return caregiverRepository.existsByLoginId(loginId);
    }

    private Caregiver saveCaregiver(CaregiverSignupRequestDto requestDto, String encodedPassword) {
        Caregiver caregiver = caregiverRepository.save(Caregiver.from(requestDto, encodedPassword));

        CaregiverInformation information = saveCaregiverInformation(requestDto.information(), caregiver);
        caregiver.updateCaregiverInformation(information);

        if(requestDto.optional() != null) {
            CaregiverOptional optional = saveCaregiverOptional(requestDto.optional(), caregiver);
            caregiver.updateCaregiverOptional(optional);
        }

        return caregiver;
    }

    private CaregiverInformation saveCaregiverInformation(InformationRequestDto request, Caregiver caregiver) {
        CaregiverInformation caregiverInformation =  caregiverInformationRepository.save(CaregiverInformation.from(request, caregiver));

        List<CaregiverLicense> licenses = saveCaregiverLicenses(request.licenses(), caregiverInformation);
        caregiverInformation.updateLicenses(licenses);

        return caregiverInformation;
    }

    private CaregiverOptional saveCaregiverOptional(OptionalRequestDto request, Caregiver caregiver) {
        return caregiverOptionalRepository.save(CaregiverOptional.from(request, caregiver));
    }

    private List<CaregiverLicense> saveCaregiverLicenses(List<LicenseRequestDto> licenseDtos, CaregiverInformation caregiverInformation) {
        List<CaregiverLicense> licenses = licenseDtos.stream()
                .map(dto -> CaregiverLicense.from(dto, caregiverInformation))
                .collect(Collectors.toList());

        caregiverLicenseRepository.saveAll(licenses);

        return licenses;
    }

    @Transactional(readOnly = true)
    private Caregiver findCaregiverById(Long caregiverId) {
        return caregiverRepository.findById(caregiverId)
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.CAREGIVER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    private Caregiver findCaregiverByIdJoinInfo(Long caregiverId) {
        return caregiverRepository.findByIdJoinInfo(caregiverId)
                .orElseThrow(() -> new ApplicationException(CaregiverErrorCase.CAREGIVER_NOT_FOUND));
    }
}
