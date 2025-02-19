package com.ongi.backend.domain.center.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.dto.request.CenterEssentialRequestDto;
import com.ongi.backend.domain.center.dto.request.CenterOptionalRequestDto;
import com.ongi.backend.domain.center.dto.request.CenterRequestDto;
import com.ongi.backend.domain.center.dto.response.CenterEssentialResponseDto;
import com.ongi.backend.domain.center.dto.response.CenterOptionalResponseDto;
import com.ongi.backend.domain.center.dto.response.CenterProfileResponseDto;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.service.CenterService;
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
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    @PostMapping(value = "/register")
    public CommonResponse<String> registerCenter(
            @Valid @RequestBody CenterRequestDto requestDto  // JSON 데이터
    ) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        // JSON 데이터 + 이미지 경로를 사용하여 센터 등록
        centerService.saveCenterInfo(centerId, requestDto);

        return CommonResponse.success("센터 등록 성공");
    }

    @GetMapping("/search")
    public CommonResponse<List<CenterResponseDto>> findCenter(@RequestParam("centerName") String centerName) {
        List<CenterResponseDto> result = centerService.findCenter(centerName);
        return CommonResponse.success(result);
    }

    @PostMapping("/profile")
    public CommonResponse<String> updateCenterProfileImage(@RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        centerService.updateCenterProfileImage(centerId, profileImage);
        return CommonResponse.success("센터 프로필 이미지를 성공적으로 등록했습니다.");
    }

    @PostMapping("/document")
    public CommonResponse<String> updateCenterDocument(@RequestParam(value = "centerDocument", required = false) MultipartFile centerDocument) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        centerService.updateCenterDocument(centerId, centerDocument);
        return CommonResponse.success("센터 증빙 자료를 성공적으로 등록했습니다.");
    }

    @PostMapping("/{centerId}/approve")
    public CommonResponse<String> approveCenterDocument(@PathVariable("centerId") Long centerId) {
        centerService.approveCenterDocument(centerId);
        return CommonResponse.success("센터 인증이 완료되었습니다.");
    }

    @DeleteMapping("/{centerId}")
    public CommonResponse<String> deleteCenter(
            @PathVariable("centerId") Long centerId) {
        centerService.deleteCenter(centerId);
        return CommonResponse.success("센터 삭제가 완료되었습니다.");
    }

    @GetMapping("/essential")
    public CommonResponse<CenterEssentialResponseDto> getCenterEssential() {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        CenterEssentialResponseDto response = centerService.getCenterEssential(centerId);
        return CommonResponse.success(response);
    }

    @PutMapping("/essential")
    public CommonResponse<Object> updateCenterEssential(@Valid @RequestBody CenterEssentialRequestDto request) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        centerService.updateCenterEssential(centerId, request);
        return CommonResponse.success();
    }

    @GetMapping("/optional")
    public CommonResponse<CenterOptionalResponseDto> getCenterOptional() {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        CenterOptionalResponseDto response = centerService.getCenterOptional(centerId);
        return CommonResponse.success(response);
    }

    @PutMapping("/optional")
    public CommonResponse<Object> updateCenterOptional(@Valid @RequestBody CenterOptionalRequestDto request) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        centerService.updateCenterOptional(centerId, request);
        return CommonResponse.success();
    }

    @GetMapping("/profile")
    public CommonResponse<CenterProfileResponseDto> getProfile() {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        CenterProfileResponseDto response = centerService.getProfile(centerId);
        return CommonResponse.success(response);
    }
}
