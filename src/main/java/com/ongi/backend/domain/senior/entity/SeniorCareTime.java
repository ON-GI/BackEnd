package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.domain.senior.dto.request.SeniorCareConditionRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SeniorCareTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "care_condition_id", nullable = false)
    private SeniorCareCondition careCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;  // 요일 (월~일)

    @Column(nullable = false)
    private LocalTime startTime;  // 캐어 시작 시간

    @Column(nullable = false)
    private LocalTime endTime;  // 캐어 종료 시간

    public static SeniorCareTime from(SeniorCareConditionRequestDto.SeniorCareTimeRequestDto requestDto, SeniorCareCondition careCondition) {
        return SeniorCareTime.builder()
                .careCondition(careCondition)
                .dayOfWeek(requestDto.dayOfWeek())
                .startTime(requestDto.startTime())
                .endTime(requestDto.endTime())
                .build();
    }
}
