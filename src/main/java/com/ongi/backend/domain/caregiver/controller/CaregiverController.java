package com.ongi.backend.domain.caregiver.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverSignupRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverUpdateRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.ValidateIdRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.dto.response.CaregiverResponseDto;
import com.ongi.backend.domain.caregiver.dto.response.CaregiverSignupResponse;
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
    public CommonResponse<CaregiverSignupResponse> registerCaregiver(@Valid @RequestBody CaregiverSignupRequestDto caregiverSignupRequestDto) {
        CaregiverSignupResponse response = caregiverService.registerCaregiver(caregiverSignupRequestDto);
        return CommonResponse.success(response);
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

    @PostMapping("/profile")
    public CommonResponse<String> registerProfileImage(@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        caregiverService.updateCaregiverProfileImage(profileImage, caregiverId);
        return CommonResponse.success("프로필 이미지 등록 완료했습니다.");
    }

    @GetMapping
    public CommonResponse<CaregiverResponseDto> getCaregiver() {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CaregiverResponseDto response = caregiverService.getCaregiver(caregiverId);
        return CommonResponse.success(response);
    }

    @PutMapping
    public CommonResponse<Object> updateCaregiver(@Valid @RequestBody CaregiverUpdateRequestDto request) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        caregiverService.updateCaregiver(caregiverId, request);
        return CommonResponse.success();
    }
}
