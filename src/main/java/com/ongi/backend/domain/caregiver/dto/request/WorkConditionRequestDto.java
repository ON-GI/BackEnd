package com.ongi.backend.domain.caregiver.dto.request;

import com.ongi.backend.domain.caregiver.entity.CaregiverWorkConditionPayType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class WorkConditionRequestDto {

    private CaregiverWorkConditionPayType payType;

    private Integer payAmount;
    private List<WorkRegionRequestDto> workRegions;
    private List<WorkTimeRequestDto> workTimes;

    @Builder
    public WorkConditionRequestDto(CaregiverWorkConditionPayType payType, Integer payAmount,
                                   List<WorkRegionRequestDto> workRegions, List<WorkTimeRequestDto> workTimes) {
        this.payType = payType;
        this.payAmount = payAmount;
        this.workRegions = workRegions;
        this.workTimes = workTimes;
    }
}