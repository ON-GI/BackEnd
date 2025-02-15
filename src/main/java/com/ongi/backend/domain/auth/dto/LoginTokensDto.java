package com.ongi.backend.domain.auth.dto;

public record LoginTokensDto(
        String accessToken,
        String refreshToken
) { }
