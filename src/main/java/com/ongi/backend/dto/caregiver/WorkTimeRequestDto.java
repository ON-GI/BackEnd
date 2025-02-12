package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import com.ongi.backend.entity.caregiver.WorkTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class WorkTimeRequestDto {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    public WorkTimeRequestDto(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WorkTime toEntity(CaregiverWorkCondition workCondition) {
        return WorkTime.builder()
                .workCondition(workCondition)
                .dayOfWeek(this.dayOfWeek)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
