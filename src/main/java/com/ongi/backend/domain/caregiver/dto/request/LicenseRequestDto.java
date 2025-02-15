package com.ongi.backend.domain.caregiver.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LicenseRequestDto (

    @NotBlank(message = "자격증 이름을 입력하세요.")
    String licenseName,

    @NotBlank(message = "자격증 번호를 입력하세요.")
    String licenseNumber,

    String licenseGrade

){ }
