package com.ongi.backend.domain.center.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.center.dto.response.CenterResponseDto;
import com.ongi.backend.domain.center.service.CenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterService centerService;

    @GetMapping("/search")
    public CommonResponse<Object> findCenterByName(@RequestParam("centerName") String centerName) {
        List<CenterResponseDto> centerResponseDtoList = centerService.findCenterByName(centerName);
        return CommonResponse.success(centerResponseDtoList);
    }
}
