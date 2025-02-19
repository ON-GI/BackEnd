package com.ongi.backend.domain.matching.dto.response;

import com.ongi.backend.domain.caregiver.dto.response.WorkRegionResponseDto;
import com.ongi.backend.domain.caregiver.dto.response.WorkTimeResponseDto;
import com.ongi.backend.domain.caregiver.entity.CaregiverWorkCondition;
import com.ongi.backend.domain.caregiver.entity.enums.CaregiverWorkConditionPayType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
public record WorkConditionResponseDto (
        CaregiverWorkConditionPayType payType,
        Integer payAmount,
        List<WorkRegionResponseDto> workRegions,
        List<WorkTimeResponseDto> workTimes
) {

    public static WorkConditionResponseDto from(CaregiverWorkCondition workCondition) {
        return WorkConditionResponseDto.builder()
                .payType(workCondition.getPayType())
                .payAmount(workCondition.getPayAmount())
                .workRegions(workCondition.getCaregiverWorkRegions().stream()
                        .map(WorkRegionResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .workTimes(workCondition.getCaregiverWorkTimes().stream()
                        .map(WorkTimeResponseDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}