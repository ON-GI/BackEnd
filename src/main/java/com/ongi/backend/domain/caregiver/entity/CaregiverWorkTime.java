package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.domain.caregiver.dto.request.WorkTimeRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CaregiverWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_condition_id", nullable = false)
    private CaregiverWorkCondition workCondition;  // 근무 조건과 다대일 (N:1)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;  // 요일 (월~일)

    @Column(nullable = false)
    private LocalTime startTime;  // 근무 시작 시간

    @Column(nullable = false)
    private LocalTime endTime;  // 근무 종료 시간

    public static CaregiverWorkTime from (WorkTimeRequestDto workTimeRequestDto, CaregiverWorkCondition workCondition) {
        return CaregiverWorkTime.builder()
                .workCondition(workCondition)
                .dayOfWeek(workTimeRequestDto.getDayOfWeek())
                .startTime(workTimeRequestDto.getStartTime())
                .endTime(workTimeRequestDto.getEndTime())
                .build();
    }

}
