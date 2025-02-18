package com.ongi.backend.domain.senior.dto.response;

import com.ongi.backend.domain.senior.entity.SeniorCareTime;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeniorCareTimeResponseDto {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static SeniorCareTimeResponseDto from(SeniorCareTime careTime) {
        return SeniorCareTimeResponseDto.builder()
                .dayOfWeek(careTime.getDayOfWeek())
                .startTime(careTime.getStartTime())
                .endTime(careTime.getEndTime())
                .build();
    }
}
