package com.ongi.backend.domain.matching.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.matching.dto.request.MatchingAdjustRequestDto;
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
@RequestMapping("/api/v1/caregiver/matching")
public class CaregiverMatchingController {

    private final MatchingService matchingService;

    @GetMapping()
    public CommonResponse<List<MatchingThumbnailResponseDto>> findAllCaregiverMatchingsByStatus(
            @RequestParam(value = "statuses", required = false) List<MatchingStatus> statuses
    ) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return CommonResponse.success(matchingService.findAllMatchingThumbnailsByCaregiverAndStatus(caregiverId, statuses));
    }

    @GetMapping("unread-count")
    public CommonResponse<Long> findCaregiverUnReadMatchingCount() {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return CommonResponse.success(matchingService.findCaregiverUnReadMatchingCount(caregiverId));
    }

    @PostMapping("/{matchingId}/read")
    public CommonResponse<String> readMatching(@PathVariable("matchingId") Long matchingId) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        matchingService.readMatching(matchingId, caregiverId);

        return CommonResponse.success("매칭 확인을 완료했습니다.");
    }


    @PostMapping("/{matchingId}/reject")
    public CommonResponse<String> rejectMatching(@PathVariable("matchingId") Long matchingId) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        matchingService.rejectMatching(matchingId, caregiverId);

        return CommonResponse.success("매칭이 거절되었습니다.");
    }

    @PostMapping("/{matchingId}/adjust")
    public CommonResponse<String> adjustMatching(
            @PathVariable("matchingId") Long matchingId,
            @Valid @RequestBody MatchingAdjustRequestDto requestDto) {
        Long caregiverId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        matchingService.adjustMatching(requestDto, matchingId, caregiverId);

        return CommonResponse.success("매칭 조율 정보가 관리자에게 전송되었습니다.");
    }
}

