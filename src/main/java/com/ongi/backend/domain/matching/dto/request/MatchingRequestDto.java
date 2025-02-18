package com.ongi.backend.domain.matching.dto.request;

import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;

public record MatchingRequestDto (
    MatchingStatus matchingStatus
) {}