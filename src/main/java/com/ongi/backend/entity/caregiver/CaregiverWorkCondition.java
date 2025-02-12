package com.ongi.backend.entity.caregiver;

import com.ongi.backend.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CaregiverWorkCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id", nullable = false)
    private Caregiver caregiver;  // 1:1 관계

    @Column(nullable = false)
    private int minHourPay;  // 시급 하한선

    @Column(nullable = false)
    private int maxHourPay;  // 시급 상한선

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkRegion> regions;  // 근무 가능 지역

    @OneToMany(mappedBy = "workCondition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkTime> workTimes;  // 근무 가능 시간

    @Builder
    public CaregiverWorkCondition(Caregiver caregiver, int minHourPay, int maxHourPay,
                                  List<WorkRegion> regions, List<WorkTime> workTimes) {
        this.caregiver = caregiver;
        this.minHourPay = minHourPay;
        this.maxHourPay = maxHourPay;
        this.regions = regions;
        this.workTimes = workTimes;
    }
}
