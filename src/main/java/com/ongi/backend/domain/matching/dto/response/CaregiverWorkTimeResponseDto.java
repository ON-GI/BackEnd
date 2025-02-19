package com.ongi.backend.domain.matching.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Builder
public class CaregiverWorkTimeResponseDto {
    private DayOfWeek dayOfWeek;  // 근무 요일
    private LocalTime startTime;  // 근무 시작 시간
    private LocalTime endTime;  // 근무 종료 시간

    public static CaregiverWorkTimeResponseDto fromEntity(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return CaregiverWorkTimeResponseDto.builder()
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
