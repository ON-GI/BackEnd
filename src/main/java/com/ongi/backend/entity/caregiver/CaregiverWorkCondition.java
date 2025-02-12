package com.ongi.backend.entity.caregiver;

import com.ongi.backend.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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
    private List<WorkRegion> workRegions;  // 근무 가능 지역

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkTime> workTimes;  // 근무 가능 시간

    @Builder
    public CaregiverWorkCondition(Caregiver caregiver, Integer minHourPay, Integer maxHourPay,
                                  List<WorkRegion> workRegions, List<WorkTime> workTimes) {
        this.caregiver = caregiver;
        this.minHourPay = minHourPay;
        this.maxHourPay = maxHourPay;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
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
