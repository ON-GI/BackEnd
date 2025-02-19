package com.ongi.backend.domain.matching.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.matching.service.MatchingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/center/matching")
public class CenterMatchingController {

    private final MatchingService matchingService;

    @GetMapping()
    public CommonResponse<List<MatchingThumbnailResponseDto>> findAllCenterMatchingsByStatus(
            @RequestParam(value = "statuses", required = false) List<MatchingStatus> statuses
    ) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return CommonResponse.success(matchingService.findAllMatchingThumbnailsByCenterAndStatus(centerId, statuses));
    }

    @PostMapping("/register")
    public CommonResponse<String> registerSenior(@Valid @RequestBody MatchingRequestDto matchingRequestDto) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        matchingService.registerMatching(matchingRequestDto, centerId);
        return CommonResponse.success("센터 정보를 성공적으로 등록했습니다.");
    }

    @PostMapping("/{matchingId}/request-caregiver")
    public CommonResponse<String> requestMatchingToCaregiver(
            @PathVariable("matchingId") Long matchingId,
            @RequestParam("caregiverId") Long caregiverId) {
        Long centerId = (Long) SecurityContextHolder.getContext().getAuthentication().getCredentials();

        matchingService.requestMatchingToCaregiver(matchingId, caregiverId, centerId);
        return CommonResponse.success("요양 보호사에게 매칭 요청을 보냈습니다.");
    }
}
