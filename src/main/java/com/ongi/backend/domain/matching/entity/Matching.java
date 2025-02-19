package com.ongi.backend.domain.matching.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE matching SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "matching")
public class Matching extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senior_id", nullable = false)
    private Senior senior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver;

    @Column(nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @Embedded
    private MatchingCondition matchingCondition;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingCareDetail> matchingCareDetails = new ArrayList<>();

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingCareTime> matchingCareTimes = new ArrayList<>();

    @Embedded
    private MatchingAdjustment matchingAdjustment;  // 매칭 조율 사항
    public static Matching from(MatchingRequestDto requestDto, Senior senior, Caregiver caregiver) {

        Matching matching = Matching.builder()
                .senior(senior)
                .caregiver(caregiver)
                .matchingCondition(MatchingCondition.from(requestDto.matchingConditionRequestDto()))
                .matchingStatus(MatchingStatus.PENDING_UNREAD)
                .matchingCareDetails(new ArrayList<>())
                .matchingCareTimes(new ArrayList<>())
                .build();

        List<MatchingCareTime> matchingCareTimes = requestDto.careTimes().stream()
                .map(matchingCareTimeRequestDto -> MatchingCareTime.from(matchingCareTimeRequestDto, matching))
                .toList();
        matching.getMatchingCareTimes().addAll(matchingCareTimes);

        List<MatchingCareDetail> matchingDetails = requestDto.careDetails().stream()
                .map(detail -> MatchingCareDetail.from(matching, SeniorCareDetail.valueOf(detail))) // ✅ String → Enum 변환
                .toList();

        matching.getMatchingCareDetails().addAll(matchingDetails);

        return matching;

    }

    public void updateMatchingStatus(MatchingStatus status) {
        this.matchingStatus = status;
    }

    public void updateMatchingAdjustment(MatchingAdjustment adjustment) {
        this.matchingAdjustment = adjustment;
    }
}
