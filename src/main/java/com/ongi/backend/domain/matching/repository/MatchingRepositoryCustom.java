package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;

import java.util.List;

public interface MatchingRepositoryCustom {
    List<MatchingThumbnailResponseDto> findMatchingsByCenterId(Long centerId);
}
