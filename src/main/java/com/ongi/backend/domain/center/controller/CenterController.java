package com.ongi.backend.domain.center.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.senior.service.SeniorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center")
public class CenterController {

    private final SeniorService seniorService;

    private final CenterService centerService;

    @GetMapping("/{centerId}/seniors")
    public CommonResponse<Object> findSenior(@PathVariable("centerId") Long centerId) {
        Center center = centerService.findCenter(centerId);
        return CommonResponse.success(seniorService.findSeniorsByCenter(center));
    }
}
