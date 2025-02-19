package com.ongi.backend.domain.matching.controller;

import com.ongi.backend.common.response.CommonResponse;
import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

