package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.dto.response.SeniorMatchingDetailResponseDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;

import java.util.List;
import java.util.Optional;

public interface MatchingRepositoryCustom {

    boolean existsByMatchingIdAndCenterId(Long matchingId, Long centerId);
    boolean existsByMatchingIdAndCaregiverId(Long matchingId, Long caregiverId);

    Long findCaregiverUnReadMatchingCount(Long caregiverId);
    List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCenterAndStatus(Long centerId, List<MatchingStatus> statuses);
    List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCaregiverAndStatus(Long caregiverId, List<MatchingStatus> statuses);

    Optional<SeniorMatchingDetailResponseDto> findSeniorMatchingDetailByMatchingId(Long matchingId);
}
