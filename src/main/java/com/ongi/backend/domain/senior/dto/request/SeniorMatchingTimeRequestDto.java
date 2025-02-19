package com.ongi.backend.domain.senior.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record SeniorMatchingTimeRequestDto (

        @NotNull(message = "요일 정보는 필수입니다.")
        DayOfWeek dayOfWeek,

        @NotNull(message = "캐어 시작 시간은 필수입니다.")
        LocalTime startTime,

        @NotNull(message = "캐어 종료 시간은 필수입니다.")
        LocalTime endTime
) {

}
