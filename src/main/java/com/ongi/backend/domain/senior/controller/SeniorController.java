package com.ongi.backend.domain.senior.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.service.SeniorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/senior")
public class SeniorController {

    private final SeniorService seniorService;

    private final CenterService centerService;

    @PostMapping("/register")
    public CommonResponse<Object> registerSenior(@Valid @RequestBody SeniorRequestDto seniorRequestDto) {
        Center center = centerService.findCenter(1L);
        seniorService.registerSenior(seniorRequestDto, center);
        return CommonResponse.success();
    }
}
