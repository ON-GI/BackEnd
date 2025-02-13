package com.ongi.backend.domain.caregiver.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.caregiver.dto.request.CaregiverRequestDto;
import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.dto.response.WorkConditionResponseDto;
import com.ongi.backend.domain.caregiver.service.CaregiverService;
import com.ongi.backend.domain.caregiver.service.CaregiverWorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

    private final CaregiverWorkService caregiverWorkService;

    @PostMapping("/signup")
    public void registerCaregiver(@RequestBody CaregiverRequestDto caregiverRequestDto) {
        caregiverService.registerCaregiver(caregiverRequestDto);
    }

    @GetMapping("/{caregiverId}/work-condition")
    public CommonResponse<WorkConditionResponseDto> getCaregiverWorkCondition(@PathVariable("caregiverId") Long caregiverId) {
        return CommonResponse.success(caregiverWorkService.getWorkConditionByCaregiverId(caregiverId));
    }

    @PostMapping("/{caregiverId}/work-condition")
    public CommonResponse<String> updateCaregiverWorkCondition(@PathVariable("caregiverId") Long caregiverId, @RequestBody WorkConditionRequestDto workConditionRequestDto) {
        caregiverWorkService.updateWorkCondition(workConditionRequestDto, caregiverId);
        return CommonResponse.success("근무 조건 업데이트 완료했습니다.");
    }
}
