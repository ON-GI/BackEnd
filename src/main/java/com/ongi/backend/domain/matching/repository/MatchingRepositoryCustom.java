package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.matching.dto.response.CaregiverMatchingResponseDto;
import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.dto.response.SeniorMatchingDetailResponseDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;

import java.util.List;
import java.util.Optional;

public interface MatchingRepositoryCustom {

    boolean existsByMatchingIdAndCenterId(Long matchingId, Long centerId);
    boolean existsByMatchingIdAndCaregiverId(Long matchingId, Long caregiverId);

    // 요양 보호사 용 읽지 않은 매칭 개수
    Long findCaregiverUnReadMatchingCount(Long caregiverId);

    // 센터 관리자 용 매칭 조회
    List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCenterAndStatus(Long centerId, List<MatchingStatus> statuses);

    // 요양 보호사 용 매칭 조회
    List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCaregiverAndStatus(Long caregiverId, List<MatchingStatus> statuses);

    // 특정 매칭과 연결된 어르신의 상세 정보 조회
    Optional<SeniorMatchingDetailResponseDto> findSeniorMatchingDetailByMatchingId(Long matchingId);

    // 매칭 알고리즘
    List<CaregiverMatchingResponseDto> findMatchingCaregivers(Long seniorId);
}
