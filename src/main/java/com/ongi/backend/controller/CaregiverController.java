package com.ongi.backend.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.dto.caregiver.CaregiverRequestDto;
import com.ongi.backend.dto.caregiver.WorkConditionRequestDto;
import com.ongi.backend.dto.caregiver.WorkConditionResponseDto;
import com.ongi.backend.service.CaregiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/caregiver")
public class CaregiverController {

    private final CaregiverService caregiverService;

    @PostMapping("/user/signup")
    public void registerCaregiver(@RequestBody CaregiverRequestDto caregiverRequestDto) {
        caregiverService.registerCaregiver(caregiverRequestDto);
    }

    @GetMapping("/{caregiverId}/work-condition")
    public CommonResponse<WorkConditionResponseDto> getCaregiverWorkCondition(@PathVariable Long caregiverId) {
        return null;
    }

    @PostMapping("/{caregiverId}/work-condition")
    public CommonResponse<String> getCaregiverWorkCondition(@PathVariable Long caregiverId, @RequestBody WorkConditionRequestDto workConditionRequestDto) {
        return CommonResponse.success("근무 조건 업데이트 완료했습니다.");
    }
}
