package com.ongi.backend.dto.caregiver;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
}