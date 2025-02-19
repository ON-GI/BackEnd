package com.ongi.backend.domain.matching.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CaregiverMatchingResponseDto {

    private Long caregiverId;  // 보호사 ID
    private String maskedName;  // 이름 마스킹된 보호사 이름
    private List<CaregiverWorkTimeResponseDto> matchingWorkTimes;  // 일치하는 근무 시간
    private Integer payAmount;  // 희망 급여
    private List<String> licenses;  // 보유 자격증 목록
    private String matchingRegion;  // 일치하는 지역 정보
    private Boolean hasDementiaTraining;  // 치매 교육 이수 여부
    private Double score;  // 계산된 매칭 점수

    /**
     * 보호사의 이름을 "XX"로 마스킹하는 정적 메서드
     */
    public static String maskName(String name) {
        if (name == null || name.length() < 2) {
            return "XX"; // 예외 처리
        }
        return name.charAt(0) + "XX";
    }

    /**
     * Caregiver 엔티티로부터 DTO를 생성하는 메서드
     */
    public static CaregiverMatchingResponseDto fromEntity(
            Long caregiverId,
            String name,
            List<CaregiverWorkTimeResponseDto> matchingWorkTimes,
            Integer payAmount,
            List<String> licenses,
            String matchingRegion,
            Boolean hasDementiaTraining,
            Double score
    ) {
        return CaregiverMatchingResponseDto.builder()
                .caregiverId(caregiverId)
                .maskedName(maskName(name))  // 이름 마스킹 적용
                .matchingWorkTimes(matchingWorkTimes)  // 일치하는 시간대만 반환
                .payAmount(payAmount)
                .licenses(licenses)
                .matchingRegion(matchingRegion)
                .hasDementiaTraining(hasDementiaTraining)  // 치매 교육 여부 추가
                .score(score)
                .build();
    }
}