package com.ongi.backend.domain.center.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.common.service.FileUploadService;
import com.ongi.backend.domain.center.dto.request.CenterRequestDto;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.service.CenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    private final FileUploadService fileUploadService;

    @PostMapping(value = "/register")
    public CommonResponse<String> registerCenter(
            @Valid  @RequestBody CenterRequestDto requestDto  // JSON 데이터
    ) {
        // JSON 데이터 + 이미지 경로를 사용하여 센터 등록
        centerService.registerCenter(requestDto);

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
}
