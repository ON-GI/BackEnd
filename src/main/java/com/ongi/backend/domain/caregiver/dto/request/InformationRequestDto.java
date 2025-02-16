package com.ongi.backend.domain.caregiver.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ongi.backend.domain.caregiver.entity.enums.DailyLivingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.FeedingAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.MobilityAssistanceType;
import com.ongi.backend.domain.caregiver.entity.enums.ToiletingAssistanceType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"toiletingAssistanceEnum", "feedingAssistanceEnum", "mobilityAssistanceEnum", "dailyLivingAssistanceEnum"})
public record InformationRequestDto(

        @NotNull(message = "치매 교육 이수 여부를 입력하세요.")
        Boolean hasDementiaTraining,

        @NotEmpty(message = "배변보조 경험을 입력하세요.")
        @Size(min = 1, message = "배변보조 경험을 입력하세요.")
        List<String> toiletingAssistance,

        @NotEmpty(message = "식사보조 경험을 입력하세요.")
        @Size(min = 1, message = "식사보조 경험을 입력하세요.")
        List<String> feedingAssistance,

        @NotEmpty(message = "이동보조 경험을 입력하세요.")
        @Size(min = 1, message = "이동보조 경험을 입력하세요.")
        List<String> mobilityAssistance,

        @NotEmpty(message = "일상생활보조 경험을 입력하세요.")
        @Size(min = 1, message = "일상생활보조 경험을 입력하세요.")
        List<String> dailyLivingAssistance,

        @Valid
        @NotEmpty(message = "자격증을 입력하세요.")
        @Size(min = 1, message = "자격증을 최소 1개 이상 입력하세요.")
        List<LicenseRequestDto> licenses

) {

    public List<ToiletingAssistanceType> getToiletingAssistanceEnum() {
        return toiletingAssistance == null ? null
                : toiletingAssistance.stream()
                .map(ToiletingAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<FeedingAssistanceType> getFeedingAssistanceEnum() {
        return feedingAssistance == null ? null
                : feedingAssistance.stream()
                .map(FeedingAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<MobilityAssistanceType> getMobilityAssistanceEnum() {
        return mobilityAssistance == null ? null
                : mobilityAssistance.stream()
                .map(MobilityAssistanceType::fromString)
                .collect(Collectors.toList());
    }

    public List<DailyLivingAssistanceType> getDailyLivingAssistanceEnum() {
        return dailyLivingAssistance == null ? null
                : dailyLivingAssistance.stream()
                .map(DailyLivingAssistanceType::fromString)
                .collect(Collectors.toList());
    }

}
