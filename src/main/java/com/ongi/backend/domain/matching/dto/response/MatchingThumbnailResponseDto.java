package com.ongi.backend.domain.matching.dto.response;

import com.ongi.backend.domain.matching.entity.Matching;
import com.ongi.backend.domain.matching.entity.MatchingCareRegion;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingThumbnailResponseDto {

    private Long matchingId;
    private String seniorName;

    private String caregiverName;

    private MatchingCareRegion careRegion;

    private MatchingStatus matchingStatus;

    private List<MatchingCareTimeResponseDto> careTimes;

    private List<String> careDetails;

    @QueryProjection
    public MatchingThumbnailResponseDto(Long matchingId, String seniorName, String caregiverName,
                                        MatchingCareRegion careRegion, MatchingStatus matchingStatus) {
        this.matchingId = matchingId;
        this.seniorName = seniorName;
        this.caregiverName = caregiverName;
        this.careRegion = careRegion;
        this.matchingStatus = matchingStatus;
    }

    public void updateCareTimes(List<MatchingCareTimeResponseDto> careTimes) {
        this.careTimes = careTimes;
    }

    public void updateCareDetails(List<String> careDetails) {
        this.careDetails = careDetails;
    }
}
