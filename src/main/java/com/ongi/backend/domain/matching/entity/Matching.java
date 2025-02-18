package com.ongi.backend.domain.matching.entity;

import com.ongi.backend.common.entity.BaseEntity;
import com.ongi.backend.domain.caregiver.entity.Caregiver;
import com.ongi.backend.domain.matching.dto.request.MatchingRequestDto;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.senior.entity.Senior;
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
@Table(name = "senior")
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

    @Column
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)  // ✅ 추가
    private List<MatchingCareDetail> matchingCareDetails = new ArrayList<>();

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)  // ✅ 추가
    private List<MatchingCareTime> matchingCareTimes = new ArrayList<>();

    public static Matching from(MatchingRequestDto matchingRequestDto) {

        return Matching.builder()
                .matchingStatus(matchingRequestDto.matchingStatus())
                .build();
    }
}
