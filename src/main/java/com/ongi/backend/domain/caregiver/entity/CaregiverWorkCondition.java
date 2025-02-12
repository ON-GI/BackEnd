package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE caregiver SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
public class CaregiverWorkCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;  // 1:1 관계

    @Column(nullable = false)
    private Integer minHourPay;  // 시급 하한선

    @Column(nullable = false)
    private Integer maxHourPay;  // 시급 상한선

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverWorkRegion> caregiverWorkRegions;  // 근무 가능 지역

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverWorkTime> caregiverWorkTimes;  // 근무 가능 시간

    public static CaregiverWorkCondition from(WorkConditionRequestDto workConditionRequestDto, Caregiver caregiver) {
        return CaregiverWorkCondition.builder()
                .caregiver(caregiver)
                .minHourPay(workConditionRequestDto.getMinHourPay())
                .maxHourPay(workConditionRequestDto.getMaxHourPay())
                .build();
    }

    public void updatePay(Integer minHourPay, Integer maxHourPay) {
        if (minHourPay != null) {
            this.minHourPay = minHourPay;
        }
        if (maxHourPay != null) {
            this.maxHourPay = maxHourPay;
        }
    }
}
