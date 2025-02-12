package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Getter
@NoArgsConstructor
public class WorkTimeResponseDto {

    private String dayOfWeek;
    private String startTime;
    private String endTime;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H시");

    private static final Map<DayOfWeek, String> DAY_OF_WEEK_MAP = Map.of(
            DayOfWeek.MONDAY, "월요일",
            DayOfWeek.TUESDAY, "화요일",
            DayOfWeek.WEDNESDAY, "수요일",
            DayOfWeek.THURSDAY, "목요일",
            DayOfWeek.FRIDAY, "금요일",
            DayOfWeek.SATURDAY, "토요일",
            DayOfWeek.SUNDAY, "일요일"
    );

    @Builder(access= AccessLevel.PRIVATE)
    public WorkTimeResponseDto(String dayOfWeek, String startTime, String endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WorkTimeResponseDto fromEntity(CaregiverWorkTime caregiverWorkTime) {
        return WorkTimeResponseDto.builder()
                .dayOfWeek(DAY_OF_WEEK_MAP.getOrDefault(caregiverWorkTime.getDayOfWeek(), "알 수 없음"))
                .startTime(formatTime(caregiverWorkTime.getStartTime()))
                .endTime(formatTime(caregiverWorkTime.getEndTime()))
                .build();
    }

    private static String formatTime(LocalTime time) {
        return time != null ? time.format(TIME_FORMATTER) : "시간 미정";
    }
}
