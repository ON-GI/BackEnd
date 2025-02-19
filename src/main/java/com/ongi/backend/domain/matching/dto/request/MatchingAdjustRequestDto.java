package com.ongi.backend.domain.matching.dto.request;

public record MatchingAdjustRequestDto(
    String adjustRequest,

    String additionalRequest,

    Boolean showCaregiverContact
) {}