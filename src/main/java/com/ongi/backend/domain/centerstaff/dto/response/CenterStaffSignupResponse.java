package com.ongi.backend.domain.centerstaff.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CenterStaffSignupResponse(
        Long id,
        String accessToken
) {
}
