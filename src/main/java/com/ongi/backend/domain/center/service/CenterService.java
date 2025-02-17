package com.ongi.backend.domain.center.service;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.service.FileUploadService;
import com.ongi.backend.domain.center.dto.request.CenterRequestDto;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.entity.enums.CenterStatus;
import com.ongi.backend.domain.center.exception.CenterErrorCase;
import com.ongi.backend.domain.center.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CenterService {

    private final CenterRepository centerRepository;

    private final CenterCodeService centerCodeService;

    private final FileUploadService fileUploadService;

    private final CenterEmailService centerEmailService;

    @Transactional
    public void saveCenterInfo(CenterRequestDto requestDto) {
        Long centerId = requestDto.centerId();

        Center center = findCenterEntity(centerId);

        center.updateCenterInfo(requestDto);
    }

    @Transactional(readOnly = true)
    public Center findCenterEntity(Long centerId) {
        return centerRepository.findById(centerId)
                .orElseThrow(() -> new ApplicationException(CenterErrorCase.CENTER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<CenterResponseDto> findCenterByName(String centerName) {
        List<Center> centers = centerRepository.findTop10ByNameStartingWith(centerName);

        return centers.stream()
                .map(CenterResponseDto::fromEntity)  // ✅ 변환 적용
                .toList();
    }

    @Transactional(readOnly = true)
    public CenterResponseDto findCenterByCode(String centerCode) {
        Center center = centerRepository.findByCenterCode(centerCode)
                .orElseThrow(() -> new ApplicationException(CenterErrorCase.CENTER_NOT_FOUND));

        return CenterResponseDto.fromEntity(center);
    }

    @Transactional
    public void updateCenterProfileImage(Long centerId, MultipartFile profileImage) {
        Center center = findCenterEntity(centerId);

        String oldProfileImageUrl = center.getProfileImageUrl();
        if (oldProfileImageUrl != null) {
            fileUploadService.deleteImage(oldProfileImageUrl);
        }

        String imageUrl = fileUploadService.uploadFileToS3(profileImage);
        center.updateProfileImageUrl(imageUrl);
    }

    // 관리자에게 센터 등록 메일 전송
    @Transactional
    public void updateCenterDocument(Long centerId, MultipartFile centerDocument) {
        Center center = findCenterEntity(centerId);

        String oldCenterDocumentUrl = center.getCenterDocumentUrl();
        if (oldCenterDocumentUrl != null) {
            fileUploadService.deleteImage(oldCenterDocumentUrl);
        }

        String centerDocumentUrl = fileUploadService.uploadFileToS3(centerDocument);
        center.updateCenterDocumentUrl(centerDocumentUrl);

        if (center.getCenterStatus().equals(CenterStatus.NOT_VERIFIED)) {
            center.updateCenterStatus(CenterStatus.PENDING_VERIFICATION);
        }

        centerEmailService.sendCenterVerificationRequestMail(center, centerDocumentUrl);
    }

    // 센터에게 승인 메일 전송
    public void approveCenterDocument(Long centerId) {
        Center center = findCenterEntity(centerId);

        if (center.getCenterCode() == null) {
            String centerCode = generateUniqueCenterCode();
            center.updateCenterCode(centerCode);
        }

        center.updateCenterStatus(CenterStatus.VERIFIED);

        centerEmailService.sendCenterApprovalMail(center);
    }

    private String generateUniqueCenterCode() {
        String centerCode;
        do {
            centerCode = centerCodeService.generateCenterCode(); // CenterCodeService에서 생성
        } while (centerRepository.existsByCenterCode(centerCode)); // 중복 체크 후 다시 생성
        return centerCode;
    }

    public void deleteCenter(Long centerId) {
        Center center = findCenterEntity(centerId);

        centerRepository.delete(center);
    }
}
