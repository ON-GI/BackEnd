package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.domain.caregiver.entity.QCaregiver;
import com.ongi.backend.domain.center.entity.QCenter;
import com.ongi.backend.domain.matching.dto.response.MatchingCareTimeResponseDto;
import com.ongi.backend.domain.matching.dto.response.MatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.dto.response.QMatchingThumbnailResponseDto;
import com.ongi.backend.domain.matching.entity.MatchingCareTime;
import com.ongi.backend.domain.matching.entity.QMatching;
import com.ongi.backend.domain.matching.entity.QMatchingCareDetail;
import com.ongi.backend.domain.matching.entity.QMatchingCareTime;
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
    public List<MatchingThumbnailResponseDto> findMatchingsByCenterId(Long centerId) {
        QMatching matching = QMatching.matching;
        QSenior senior = QSenior.senior;
        QCaregiver caregiver = QCaregiver.caregiver;
        QMatchingCareTime careTime = QMatchingCareTime.matchingCareTime;
        QMatchingCareDetail careDetail = QMatchingCareDetail.matchingCareDetail;

        List<MatchingThumbnailResponseDto> matchings = queryFactory
                .select(new QMatchingThumbnailResponseDto(
                        matching.id,
                        senior.name,
                        caregiver.name.coalesce("미배정"),  // caregiver가 없을 경우 "미배정"
                        matching.matchingCondition.matchingCareRegion,
                        matching.matchingStatus
                ))
                .from(matching)
                .join(matching.senior, senior)
                .leftJoin(matching.caregiver, caregiver)  // caregiver는 없을 수도 있음
                .where(senior.center.id.eq(centerId))
                .fetch();

        // 2️⃣ careTimes 매핑 (List 형태로 변환)
        Map<Long, List<MatchingCareTimeResponseDto>> careTimesMap = queryFactory
                .select(careTime.matching.id, careTime)
                .from(careTime)
                .where(careTime.matching.id.in(
                        matchings.stream().map(m -> m.getMatchingId()).toList()))
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
                .where(careDetail.matching.id.in(
                        matchings.stream().map(m -> m.getMatchingId()).toList()))
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
