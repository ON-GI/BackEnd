package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;

import java.util.List;

public interface MatchingRepositoryCustom {

    boolean existsByMatchingIdAndCenterId(Long matchingId, Long centerId);
    List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCenterAndStatus(Long centerId, List<MatchingStatus> statuses);
}
