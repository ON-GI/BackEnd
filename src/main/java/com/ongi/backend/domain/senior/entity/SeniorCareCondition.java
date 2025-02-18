package com.ongi.backend.domain.senior.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.senior.dto.request.SeniorCareConditionRequestDto;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE senior_care_condition SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class SeniorCareCondition extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    private LocalDate careStartDate;

    private LocalDate careEndDate;


    // 어르신이 필요한 서비스 항목들 (N:M 관계)
    @OneToMany(mappedBy = "seniorCareCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeniorCareTypeMapping> careTypes = new ArrayList<>();

    @OneToMany(mappedBy = "careCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeniorCareTime> seniorCareTimes;   // 캐어 필요 시간


    public static SeniorCareCondition from(SeniorCareConditionRequestDto requestDto, Senior senior) {

        SeniorCareCondition seniorCareCondition = SeniorCareCondition.builder()
                .senior(senior)
                .careStartDate(requestDto.careStartDate())
                .careEndDate(requestDto.careEndDate())
                .careTypes(new ArrayList<>())  // 초기화
                .seniorCareTimes(new ArrayList<>()) // 초기화
                .build();

        // CareDetails를 SeniorCareDetail Enum으로 변환 후 SeniorCareTypeMapping 테이블에 저장
        List<SeniorCareTypeMapping> careTypeMappings = requestDto.careDetails().stream()
                .map(detail -> SeniorCareTypeMapping.builder()
                        .seniorCareCondition(seniorCareCondition)
                        .seniorCareDetail(SeniorCareDetail.valueOf(detail))  // String → Enum 변환
                        .build())
                .toList();

        seniorCareCondition.getCareTypes().addAll(careTypeMappings);

        // CareTimes를 SeniorCareTime 엔티티로 변환하여 저장
        List<SeniorCareTime> careTimes = requestDto.careTimes().stream()
                .map(careTimeRequestDto -> SeniorCareTime.from(careTimeRequestDto, seniorCareCondition))
                .toList();

        seniorCareCondition.getSeniorCareTimes().addAll(careTimes);

        return seniorCareCondition;
    }

    public void updateCareCondition(SeniorCareConditionRequestDto request) {

        this.careStartDate = request.careStartDate();
        this.careEndDate = request.careEndDate();

        // 기존 careTypes와 seniorCareTimes 리스트를 초기화
        this.careTypes.clear();
        this.seniorCareTimes.clear();

        // 새로운 careTypes 추가
        this.careTypes.addAll(
                request.careDetails().stream()
                        .map(detail -> SeniorCareTypeMapping.builder()
                                .seniorCareCondition(this)
                                .seniorCareDetail(SeniorCareDetail.valueOf(detail))
                                .build())
                        .toList()
        );

        // 새로운 careTimes 추가
        this.seniorCareTimes.addAll(
                request.careTimes().stream()
                        .map(time -> SeniorCareTime.from(time, this))
                        .toList()
        );
    }
}
