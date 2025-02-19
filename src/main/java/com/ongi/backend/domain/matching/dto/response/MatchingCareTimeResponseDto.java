package com.ongi.backend.domain.matching.dto.response;

import com.ongi.backend.domain.matching.entity.MatchingCareTime;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MatchingCareTimeResponseDto {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public static MatchingCareTimeResponseDto from(MatchingCareTime careTime) {
        return MatchingCareTimeResponseDto.builder()
                .dayOfWeek(careTime.getDayOfWeek())
                .startTime(careTime.getStartTime())
                .endTime(careTime.getEndTime())
                .build();
    }
}
