package com.ongi.backend.domain.caregiver.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class WorkConditionRequestDto {

    private Integer minHourPay;
    private Integer maxHourPay;
    private List<WorkRegionRequestDto> workRegions;
    private List<WorkTimeRequestDto> workTimes;

    @Builder
    public WorkConditionRequestDto(Integer minHourPay, Integer maxHourPay,
                                   List<WorkRegionRequestDto> workRegions, List<WorkTimeRequestDto> workTimes) {
        this.minHourPay = minHourPay;
        this.maxHourPay = maxHourPay;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
    }
}