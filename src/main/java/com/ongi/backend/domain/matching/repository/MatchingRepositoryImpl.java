package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.caregiver.entity.QCaregiver;
import com.ongi.backend.domain.matching.dto.response.MatchingCareTimeResponseDto;
import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.dto.response.QMatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.entity.MatchingCareTime;
import com.ongi.backend.domain.matching.entity.QMatching;
import com.ongi.backend.domain.matching.entity.QMatchingCareDetail;
import com.ongi.backend.domain.matching.entity.QMatchingCareTime;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.senior.entity.QSenior;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchingRepositoryImpl implements MatchingRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MatchingRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public boolean existsByMatchingIdAndCenterId(Long matchingId, Long centerId) {
        QMatching matching = QMatching.matching;
        QSenior senior = QSenior.senior;

        // 매칭 ID와 센터 ID가 일치하는 매칭이 존재하는지 확인
        Integer fetchOne = queryFactory
                .selectOne()
                .from(matching)
                .join(matching.senior, senior)
                .where(matching.id.eq(matchingId)
                        .and(senior.center.id.eq(centerId)))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCenterAndStatus(Long centerId, List<MatchingStatus> statuses) {
        QMatching matching = QMatching.matching;
        QSenior senior = QSenior.senior;
        QCaregiver caregiver = QCaregiver.caregiver;
        QMatchingCareTime careTime = QMatchingCareTime.matchingCareTime;
        QMatchingCareDetail careDetail = QMatchingCareDetail.matchingCareDetail;

        // 1️⃣ 기본 매칭 정보 조회
        List<MatchingThumbnailResponseDto> matchings = queryFactory
                .select(new QMatchingThumbnailResponseDto(
                        matching.id,
                        senior.name,
                        caregiver.name.coalesce("미배정"), // caregiver 없으면 "미배정"
                        matching.matchingCondition.matchingCareRegion,
                        matching.matchingStatus
                ))
                .from(matching)
                .join(matching.senior, senior)
                .leftJoin(matching.caregiver, caregiver)
                .where(
                        senior.center.id.eq(centerId)
                                .and(statuses == null || statuses.isEmpty() ? null : matching.matchingStatus.in(statuses))
                )
                .orderBy(matching.createdAt.desc())
                .fetch();

        List<Long> matchingIds = matchings.stream().map(MatchingThumbnailResponseDto::getMatchingId).toList();

        // 2️⃣ careTimes 매핑 (List 형태로 변환)
        Map<Long, List<MatchingCareTimeResponseDto>> careTimesMap = queryFactory
                .select(careTime.matching.id, careTime)
                .from(careTime)
                .where(careTime.matching.id.in(matchingIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, Long.class),
                        Collectors.mapping(tuple -> MatchingCareTimeResponseDto.from(tuple.get(1, MatchingCareTime.class)),
                                Collectors.toList()))
                );

        // 3️⃣ careDetails 매핑 (List 형태로 변환)
        Map<Long, List<String>> careDetailsMap = queryFactory
                .select(careDetail.matching.id, careDetail.careDetail)
                .from(careDetail)
                .where(careDetail.matching.id.in(matchingIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, Long.class),
                        Collectors.mapping(tuple -> tuple.get(1, SeniorCareDetail.class).getDescription(),
                                Collectors.toList()))
                );

        // 4️⃣ DTO에 careTimes, careDetails 추가
        matchings.forEach(dto -> {
            dto.updateCareTimes(careTimesMap.getOrDefault(dto.getMatchingId(), List.of()));
            dto.updateCareDetails(careDetailsMap.getOrDefault(dto.getMatchingId(), List.of()));
        });

        return matchings;
    }
}
