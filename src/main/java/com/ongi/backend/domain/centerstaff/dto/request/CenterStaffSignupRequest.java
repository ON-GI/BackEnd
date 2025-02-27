package com.ongi.backend.domain.centerstaff.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CenterStaffSignupRequest(

        @NotBlank(message = "아이디를 입력하세요.")
        String loginId,

        @NotBlank(message = "비밀번호를 입력하세요.")
        String password,

        @NotBlank(message = "역할을 입력하세요.")
        String authority,

        String centerCode,

        Long centerId
) {
}
