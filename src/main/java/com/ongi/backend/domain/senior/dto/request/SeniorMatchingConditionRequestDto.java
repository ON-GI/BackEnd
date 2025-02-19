package com.ongi.backend.domain.senior.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record SeniorMatchingConditionRequestDto(
        @NotNull(message = "근무 시작 날짜는 필수입니다.")
        LocalDate careStartDate,

        @NotNull(message = "근무 종료 날짜는 필수입니다.")
        LocalDate careEndDate,

        String benefits, // 복리후생

        @NotNull(message = "최소 시급은 필수입니다.")
        Integer minPayAmount,

        @NotNull(message = "최대 시급은 필수입니다.")
        Integer maxPayAmount,

        @Valid
        @NotNull(message = "캐어 시간대는 최소 1개 이상 필요합니다.")
        List<SeniorMatchingTimeRequestDto> matchingTimes,

        @NotNull(message = "캐어 항목은 최소 1개 이상 선택해야 합니다.")
        List<String> careDetails
) {}