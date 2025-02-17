package com.ongi.backend.domain.senior.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.service.SeniorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/senior")
public class SeniorController {

    private final SeniorService seniorService;

    private final CenterService centerService;

    @GetMapping("/{seniorId}")
    public CommonResponse<Object> findSenior(@PathVariable("seniorId") Long seniorId) {
        return CommonResponse.success(seniorService.findSenior(seniorId));
    }

    @PostMapping()
    public CommonResponse<Object> registerSenior(@Valid @RequestBody SeniorRequestDto seniorRequestDto) {
        Center center = centerService.findCenter(1L);
        seniorService.registerSenior(seniorRequestDto, center);
        return CommonResponse.success();
    }

    @PutMapping("/{seniorId}")
    public CommonResponse<Object> updateSenior(@Valid @RequestBody SeniorRequestDto seniorRequestDto, @PathVariable("seniorId") Long seniorId) {
        Center center = centerService.findCenter(1L);
        seniorService.updateSenior(seniorId, seniorRequestDto, center);
        return CommonResponse.success();
    }

    @DeleteMapping("/{seniorId}")
    public CommonResponse<Object> updateSenior(@PathVariable("seniorId") Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return CommonResponse.success();
    }
}
