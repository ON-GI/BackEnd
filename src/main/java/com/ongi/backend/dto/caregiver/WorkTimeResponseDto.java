package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.WorkTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WorkTimeResponseDto {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    public WorkTimeResponseDto(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WorkTimeResponseDto fromEntity(WorkTime workTime) {
        return WorkTimeResponseDto.builder()
                .dayOfWeek(workTime.getDayOfWeek())
                .startTime(workTime.getStartTime())
                .endTime(workTime.getEndTime())
                .build();
    }
}
