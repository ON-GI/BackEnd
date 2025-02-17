package com.ongi.backend.domain.center.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.dto.request.CenterRequestDto;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.service.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        // JSON 데이터 + 이미지 경로를 사용하여 센터 등록
        centerService.saveCenterInfo(requestDto);

        return CommonResponse.success("센터 등록 성공");
    }

    @GetMapping("/search/name")
    public CommonResponse<Object> findCenterByName(@RequestParam("centerName") String centerName) {
        List<CenterResponseDto> centerResponseDtoList = centerService.findCenterByName(centerName);
        return CommonResponse.success(centerResponseDtoList);
    }

    @GetMapping("/search/code")
    public CommonResponse<Object> findCenterByCode(@RequestParam("centerCode") String centerCode) {
        CenterResponseDto centerResponseDto = centerService.findCenterByCode(centerCode);
        return CommonResponse.success(centerResponseDto);
    }

    @PostMapping("/{centerId}/profile")
    public CommonResponse<Object> updateCenterProfileImage(
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @PathVariable("centerId") Long centerId) {
        centerService.updateCenterProfileImage(centerId, profileImage);
        return CommonResponse.success("센터 프로필 이미지를 성공적으로 등록했습니다.");
    }

    @PostMapping("/{centerId}/document")
    public CommonResponse<Object> updateCenterDocument(
            @RequestParam(value = "centerDocument", required = false) MultipartFile centerDocument,
            @PathVariable("centerId") Long centerId) {
        centerService.updateCenterDocument(centerId, centerDocument);
        return CommonResponse.success("센터 증빙 자료를 성공적으로 등록했습니다.");
    }

    @PostMapping("/{centerId}/approve")
    public CommonResponse<Object> approveCenterDocument(
            @PathVariable("centerId") Long centerId) {
        centerService.approveCenterDocument(centerId);
        return CommonResponse.success("센터 인증이 완료되었습니다.");
    }

    @DeleteMapping("/{centerId}")
    public CommonResponse<Object> deleteCenter(
            @PathVariable("centerId") Long centerId) {
        centerService.deleteCenter(centerId);
        return CommonResponse.success("센터 삭제가 완료되었습니다.");
    }
}
