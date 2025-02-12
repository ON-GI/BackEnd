package com.ongi.backend.entity.caregiver;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class WorkTime {

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

    @Builder
    public WorkTime(CaregiverWorkCondition workCondition, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.workCondition = workCondition;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
