package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.senior.dto.request.SeniorMatchingTimeRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE senior_matching_time SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SeniorMatchingTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;  // 요일 (월~일)

    @Column(nullable = false)
    private LocalTime startTime;  // 캐어 시작 시간

    @Column(nullable = false)
    private LocalTime endTime;  // 캐어 종료 시간

    public static SeniorMatchingTime from(SeniorMatchingTimeRequestDto requestDto, Senior senior) {
        return SeniorMatchingTime.builder()
                .senior(senior)
                .dayOfWeek(requestDto.dayOfWeek())
                .startTime(requestDto.startTime())
                .endTime(requestDto.endTime())
                .build();
    }
}
