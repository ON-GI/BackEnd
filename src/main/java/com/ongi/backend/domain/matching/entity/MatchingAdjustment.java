package com.ongi.backend.domain.matching.entity;

import com.ongi.backend.domain.matching.dto.request.MatchingAdjustRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingAdjustment {

    @Column
    private String adjustRequest;

    @Column
    private String additionalRequest;

    public static MatchingAdjustment from(MatchingAdjustRequestDto requestDto) {
        return MatchingAdjustment.builder()
                .adjustRequest(requestDto.adjustRequest())
                .additionalRequest(requestDto.additionalRequest())
                .build();
    }
}
