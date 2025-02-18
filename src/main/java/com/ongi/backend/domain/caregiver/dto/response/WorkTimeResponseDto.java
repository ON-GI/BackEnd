package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class WorkTimeResponseDto {

    private String dayOfWeek;
    private String startTime;
    private String endTime;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H");

    @Builder(access= AccessLevel.PRIVATE)
    public WorkTimeResponseDto(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WorkTimeResponseDto fromEntity(CaregiverWorkTime caregiverWorkTime) {
        return WorkTimeResponseDto.builder()
                .dayOfWeek(caregiverWorkTime.getDayOfWeek().toString())
                .startTime(formatTime(caregiverWorkTime.getStartTime()))
                .endTime(formatTime(caregiverWorkTime.getEndTime()))
                .build();
    }

    private static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : "시간 미정";
    }
}
