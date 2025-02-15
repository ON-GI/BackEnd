package com.ongi.backend.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CaregiverLoginRequest(

        @NotBlank(message = "아이디를 입력하세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요.")
        String password
) { }
