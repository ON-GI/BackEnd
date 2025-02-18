package com.ongi.backend.domain.matching.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.service.MatchingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matching/center")
public class CenterMatchingController {

    private final MatchingService matchingService;

    @PostMapping("/register")
    public CommonResponse<String> registerSenior(@Valid @RequestBody MatchingRequestDto matchingRequestDto) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        matchingService.registerMatching(matchingRequestDto, centerId);
        return CommonResponse.success("센터 정보를 성공적으로 등록했습니다.");
    }
}
