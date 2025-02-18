package com.ongi.backend.domain.caregiver.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.dto.request.WorkConditionRequestDto;
import com.ongi.backend.domain.caregiver.entity.enums.CaregiverWorkConditionPayType;
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
@SQLDelete(sql = "UPDATE caregiver_work_condition SET deleted_at = now() WHERE id = ?")  // soft delete
@SQLRestriction("deleted_at IS NULL")
public class CaregiverWorkCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;  // 1:1 관계

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaregiverWorkConditionPayType payType;  // 시급, 일급, 월급 중 선택

    @Column(nullable = false)
    private Integer payAmount;  // 원하는 급여 금액

    @Column(nullable = false)
    private Boolean negotiable;

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverWorkRegion> caregiverWorkRegions;  // 근무 가능 지역

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CaregiverWorkTime> caregiverWorkTimes;  // 근무 가능 시간

    public static CaregiverWorkCondition from(WorkConditionRequestDto workConditionRequestDto, Caregiver caregiver) {
        return CaregiverWorkCondition.builder()
                .caregiver(caregiver)
                .payType(workConditionRequestDto.getPayType())
                .payAmount(workConditionRequestDto.getPayAmount())
                .negotiable(workConditionRequestDto.getNegotiable())
                .build();
    }

    public void updatePay(CaregiverWorkConditionPayType payType, Integer payAmount) {
        if (payType != null) {
            this.payType = payType;
        }
        if (payAmount != null) {
            this.payAmount = payAmount;
        }
    }
}
