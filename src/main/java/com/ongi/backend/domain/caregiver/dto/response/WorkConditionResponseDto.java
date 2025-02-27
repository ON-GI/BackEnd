package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.enums.CaregiverWorkConditionPayType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class WorkConditionResponseDto {

    private CaregiverWorkConditionPayType payType;
    private Integer payAmount;

    private Boolean negotiable;
    private List<WorkRegionResponseDto> workRegions;
    private List<WorkTimeResponseDto> workTimes;

    @Builder(access = AccessLevel.PRIVATE)
    public WorkConditionResponseDto(CaregiverWorkConditionPayType payType, Integer payAmount, Boolean negotiable,
                                    List<WorkRegionResponseDto> workRegions, List<WorkTimeResponseDto> workTimes) {
        this.payType = payType;
        this.payAmount = payAmount;
        this.negotiable = negotiable;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
    }

    public static WorkConditionResponseDto fromEntity(CaregiverWorkCondition workCondition) {
        return WorkConditionResponseDto.builder()
                .payType(workCondition.getPayType())
                .payAmount(workCondition.getPayAmount())
                .negotiable(workCondition.getNegotiable())
                .workRegions(workCondition.getCaregiverWorkRegions().stream()
                        .map(WorkRegionResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .workTimes(workCondition.getCaregiverWorkTimes().stream()
                        .map(WorkTimeResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}