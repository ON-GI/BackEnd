package com.ongi.backend.dto.caregiver;

import com.ongi.backend.entity.caregiver.CaregiverWorkCondition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class WorkConditionResponseDto {

    private int minHourPay;
    private int maxHourPay;
    private List<WorkRegionResponseDto> workRegions;
    private List<WorkTimeResponseDto> workTimes;

    @Builder
    public WorkConditionResponseDto(int minHourPay, int maxHourPay,
                                    List<WorkRegionResponseDto> workRegions, List<WorkTimeResponseDto> workTimes) {
        this.minHourPay = minHourPay;
        this.maxHourPay = maxHourPay;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
    }

    public static WorkConditionResponseDto fromEntity(CaregiverWorkCondition workCondition) {
        return WorkConditionResponseDto.builder()
                .minHourPay(workCondition.getMinHourPay())
                .maxHourPay(workCondition.getMaxHourPay())
                .workRegions(workCondition.getWorkRegions().stream()
                        .map(WorkRegionResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .workTimes(workCondition.getWorkTimes().stream()
                        .map(WorkTimeResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}