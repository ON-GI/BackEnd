package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.Caregiver;
import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import com.ongi.backend.entity.caregiver.WorkRegion;
import com.ongi.backend.entity.caregiver.WorkTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class CaregiverWorkConditionDto {

    private int minHourPay;
    private int maxHourPay;
    private List<WorkRegionDto> workRegions;
    private List<WorkTimeDto> workTimes;

    @Builder
    public CaregiverWorkConditionDto(int minHourPay, int maxHourPay,
                                     List<WorkRegionDto> workRegions, List<WorkTimeDto> workTimes) {
        this.minHourPay = minHourPay;
        this.maxHourPay = maxHourPay;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
    }

    public CaregiverWorkCondition toEntity(Caregiver caregiver) {
        return CaregiverWorkCondition.builder()
                .caregiver(caregiver)
                .minHourPay(this.minHourPay)
                .maxHourPay(this.maxHourPay)
                .build();
    }
}