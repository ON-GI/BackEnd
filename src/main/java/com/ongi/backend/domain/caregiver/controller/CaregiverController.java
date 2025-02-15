package com.ongi.backend.domain.caregiver.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.ValidateIdRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.dto.response.WorkConditionResponseDto;
import com.ongi.backend.domain.caregiver.service.CaregiverService;
import com.ongi.backend.domain.caregiver.service.CaregiverWorkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;
    private final CaregiverWorkService caregiverWorkService;

    @PostMapping("/validate-id")
    public CommonResponse<Object> validateId(@Valid @RequestBody ValidateIdRequestDto requestDto) {
        caregiverService.validateId(requestDto);
        return CommonResponse.success();
    }

    @PostMapping("/signup")
    public CommonResponse<Object> registerCaregiver(@Valid @RequestBody CaregiverRequestDto caregiverRequestDto) {
        caregiverService.registerCaregiver(caregiverRequestDto);
        return CommonResponse.success();
    }

    @GetMapping("/work-condition")
    public CommonResponse<WorkConditionResponseDto> getCaregiverWorkCondition() {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return CommonResponse.success(caregiverWorkService.getWorkConditionByCaregiverId(caregiverId));
    }

    @PostMapping("/work-condition")
    public CommonResponse<String> updateCaregiverWorkCondition(@RequestBody WorkConditionRequestDto workConditionRequestDto) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        caregiverWorkService.updateWorkCondition(workConditionRequestDto, caregiverId);
        return CommonResponse.success("근무 조건 업데이트 완료했습니다.");
    }

    @PostMapping("/{caregiverId}/profile")
    public CommonResponse<String> registerProfileImage(@RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                                                       @PathVariable("caregiverId") Long caregiverId) {
        caregiverService.updateCaregiverProfileImage(profileImage, caregiverId);
        return CommonResponse.success("프로필 이미지 등록 완료했습니다.");
    }
}
