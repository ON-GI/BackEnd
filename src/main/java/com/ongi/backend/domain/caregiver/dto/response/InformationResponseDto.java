package com.ongi.backend.domain.caregiver.dto.response;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.caregiver.entity.CaregiverInformation;
import com.ongi.backend.domain.caregiver.entity.enums.DailyLivingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.FeedingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.MobilityAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.ToiletingAssistanceType;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record InformationResponseDto(
        Boolean hasDementiaTraining,
        List<ToiletingAssistanceType> toiletingAssistance,
        List<FeedingAssistanceType> feedingAssistance,
        List<MobilityAssistanceType> mobilityAssistance,
        List<DailyLivingAssistanceType> dailyLivingAssistance,
        List<LicenseResponseDto> licenses
) {

    public static InformationResponseDto from(CaregiverInformation information) {
        List<LicenseResponseDto> licenses = information.getLicenses().stream()
                .map(LicenseResponseDto::from)
                .toList();

        return InformationResponseDto.builder()
                .hasDementiaTraining(information.isHasDementiaTraining())
                .toiletingAssistance(information.getToiletingAssistance())
                .feedingAssistance(information.getFeedingAssistance())
                .mobilityAssistance(information.getMobilityAssistance())
                .dailyLivingAssistance(information.getDailyLivingAssistance())
                .licenses(licenses)
                .build();
    }

}
