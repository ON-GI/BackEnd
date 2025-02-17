package com.ongi.backend.domain.senior.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record SeniorCareConditionRequestDto(
        @NotEmpty(message = "어르신의 필요한 서비스 목록을 입력하세요.")
        List<String> careDetails,  // Enum으로 변환 (SeniorCareDetail)

        LocalDate careStartDate,

        LocalDate careEndDate,

        @Valid
        @NotNull(message = "캐어 시간이 입력되어야 합니다.")
        List<SeniorCareTimeRequestDto> careTimes
) {

    public record SeniorCareTimeRequestDto(
            @NotNull(message = "요일을 입력하세요.") DayOfWeek dayOfWeek,
            @NotNull(message = "캐어 시작 시간을 입력하세요.") LocalTime startTime,
            @NotNull(message = "캐어 종료 시간을 입력하세요.") LocalTime endTime
    ) { }
}
