package com.ongi.backend.domain.centerstaff.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.centerstaff.dto.request.CenterStaffSignupRequest;
import com.ongi.backend.domain.centerstaff.dto.response.CenterStaffResponse;
import com.ongi.backend.domain.centerstaff.dto.response.CenterStaffSignupResponse;
import com.ongi.backend.domain.centerstaff.service.CenterStaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center-staff")
public class CenterStaffController {

    private final CenterStaffService centerStaffService;

    @PostMapping("/signup")
    public CommonResponse<CenterStaffSignupResponse> signup(@Valid @RequestBody CenterStaffSignupRequest request) {
        CenterStaffSignupResponse response = centerStaffService.signup(request);
        return CommonResponse.success(response);
    }
}
