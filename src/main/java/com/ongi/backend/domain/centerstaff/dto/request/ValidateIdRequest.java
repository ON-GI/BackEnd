package com.ongi.backend.domain.centerstaff.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ValidateIdRequest (
    @NotBlank(message = "아이디를 입력해주세요.")
    String loginId
) { }
