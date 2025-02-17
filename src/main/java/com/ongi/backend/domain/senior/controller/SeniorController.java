package com.ongi.backend.domain.senior.controller;

import com.ongi.backend.common.exception.ApplicationException;
import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.auth.exception.AuthErrorCase;
import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.center.service.CenterService;
import com.ongi.backend.domain.senior.dto.request.SeniorRequestDto;
import com.ongi.backend.domain.senior.service.SeniorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/senior")
public class SeniorController {

    private final SeniorService seniorService;

    @GetMapping("/{seniorId}")
    public CommonResponse<Object> findSenior(@PathVariable("seniorId") Long seniorId) {
        return CommonResponse.success(seniorService.findSenior(seniorId));
    }

    @GetMapping("")
    public CommonResponse<Object> findSeniors() {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        return CommonResponse.success(seniorService.findSeniorsByCenter(centerId));
    }

    @PostMapping()
    public CommonResponse<Object> registerSenior(@Valid @RequestBody SeniorRequestDto seniorRequestDto) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (centerId == null) {
            throw new ApplicationException(AuthErrorCase.INVALID_AUTHORITY);
        }

        seniorService.registerSenior(seniorRequestDto, centerId);
        return CommonResponse.success("어르신 정보를 성공적으로 등록했습니다.");
    }

    @PostMapping("/{seniorId}")
    public CommonResponse<Object> updateSenior(@Valid @RequestBody SeniorRequestDto seniorRequestDto, @PathVariable("seniorId") Long seniorId) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (centerId == null) {
            throw new ApplicationException(AuthErrorCase.INVALID_AUTHORITY);
        }

        seniorService.updateSenior(seniorId, seniorRequestDto, centerId);
        return CommonResponse.success("어르신 정보를 성공적으로 수정했습니다.");
    }

    @PostMapping("/{seniorId}/profile")
    public CommonResponse<Object> updateSeniorProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @PathVariable("seniorId") Long seniorId) {
        seniorService.updateSeniorProfileImage(seniorId, profileImage);
        return CommonResponse.success("어르신 프로필 이미지를 성공적으로 등록했습니다.");
    }

    @DeleteMapping("/{seniorId}")
    public CommonResponse<Object> updateSenior(@PathVariable("seniorId") Long seniorId) {
        seniorService.deleteSenior(seniorId);
        return CommonResponse.success("어르신 정보를 성공적으로 삭제했습니다.");
    }
}
