package com.ongi.backend.domain.senior.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SeniorDiseaseRequestDto(
        @NotEmpty(message = "질병명을 입력하세요.") String disease,

        String additionalDementiaSymptoms, // 기타 치매 증상 (입력 가능)

        @Valid
        //@NotEmpty(message = "어르신이 겪고 있는 치매 증상을 입력하세요.")
        List<String> dementiaSymptoms  // Enum으로 변환 (DementiaSymptom)
) { }