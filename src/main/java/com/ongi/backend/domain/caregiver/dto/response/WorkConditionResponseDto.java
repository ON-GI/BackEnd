package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class WorkConditionResponseDto {

    private Integer minHourPay;
    private Integer maxHourPay;
    private List<WorkRegionResponseDto> workRegions;
    private List<WorkTimeResponseDto> workTimes;

    @Builder(access= AccessLevel.PRIVATE)
    public WorkConditionResponseDto(Integer minHourPay, Integer maxHourPay,
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
                .workRegions(workCondition.getCaregiverWorkRegions().stream()
                        .map(WorkRegionResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .workTimes(workCondition.getCaregiverWorkTimes().stream()
                        .map(WorkTimeResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}