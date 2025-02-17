package com.ongi.backend.domain.senior.dto.response;

import com.ongi.backend.domain.senior.entity.SeniorCareCondition;
import com.ongi.backend.domain.senior.entity.SeniorCareTypeMapping;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SeniorCareConditionResponseDto {

    private List<CareDetailResponse> careDetails; // 필요한 서비스 Enum 리스트
    private List<SeniorCareTimeResponseDto> careTimes; // 케어 시간 리스트

    public static SeniorCareConditionResponseDto from(SeniorCareCondition careCondition) {
        return SeniorCareConditionResponseDto.builder()
                .careDetails(careCondition.getCareTypes().stream()
                        .map(mapping -> CareDetailResponse.from(mapping.getSeniorCareDetail())) // CareDetailResponse 변환
                        .toList())
                .careTimes(careCondition.getSeniorCareTimes().stream()
                        .map(SeniorCareTimeResponseDto::from) // SeniorCareTimeResponseDto 리스트로 변환
                        .toList())
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @Builder
    public static class CareDetailResponse {
        private String type; // "식사보조"
        private String description; // "같이 밥 먹기"

        public static CareDetailResponse from(SeniorCareDetail careDetail) {
            return CareDetailResponse.builder()
                    .type(careDetail.getType().getDescription()) // SeniorCareType의 description
                    .description(careDetail.getDescription()) // SeniorCareDetail의 description
                    .build();
        }
    }
}