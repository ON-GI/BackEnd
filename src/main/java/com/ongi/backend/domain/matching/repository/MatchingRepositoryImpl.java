package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.common.enums.Gender;
import com.ongi.backend.domain.caregiver.entity.QCaregiver;
import com.ongi.backend.domain.matching.dto.response.*;
import com.ongi.backend.domain.matching.entity.MatchingCareTime;
import com.ongi.backend.domain.matching.entity.QMatching;
import com.ongi.backend.domain.matching.entity.QMatchingCareDetail;
import com.ongi.backend.domain.matching.entity.QMatchingCareTime;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.senior.entity.QDiseaseDementiaMapping;
import com.ongi.backend.domain.senior.entity.QSenior;
import com.ongi.backend.domain.senior.entity.QSeniorDisease;
import com.ongi.backend.domain.senior.entity.enums.DementiaSymptom;
import com.ongi.backend.domain.senior.entity.enums.GradeType;
import com.ongi.backend.domain.senior.entity.enums.ResidenceType;
import com.ongi.backend.domain.senior.entity.enums.SeniorCareDetail;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public boolean existsByMatchingIdAndCaregiverId(Long matchingId, Long caregiverId) {
        QMatching matching = QMatching.matching;
        QCaregiver caregiver = QCaregiver.caregiver;

        // 매칭 ID와 센터 ID가 일치하는 매칭이 존재하는지 확인
        Integer fetchOne = queryFactory
                .selectOne()
                .from(matching)
                .join(matching.caregiver, caregiver)
                .where(matching.id.eq(matchingId)
                        .and(caregiver.id.eq(caregiverId)))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public Long findCaregiverUnReadMatchingCount(Long caregiverId) {
        QMatching matching = QMatching.matching;

        return queryFactory
                .select(matching.count())
                .from(matching)
                .where(matching.caregiver.id.eq(caregiverId)
                        .and(matching.matchingStatus.eq(MatchingStatus.PENDING_UNREAD))) // 상태 필터링
                .fetchOne();  // 개수 조회
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

    @Override
    public List<MatchingThumbnailResponseDto> findAllMatchingThumbnailsByCaregiverAndStatus(Long caregiverId, List<MatchingStatus> statuses) {
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
                        caregiver.name, // caregiver는 여기서는 무조건 존재
                        matching.matchingCondition.matchingCareRegion,
                        matching.matchingStatus
                ))
                .from(matching)
                .join(matching.senior, senior)
                .join(matching.caregiver, caregiver)
                .where(
                        caregiver.id.eq(caregiverId)
                                .and(statuses == null || statuses.isEmpty() ? null : matching.matchingStatus.in(statuses))
                )
                .orderBy(matching.createdAt.desc()) // 최신순 정렬
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

    @Override
    public Optional<SeniorMatchingDetailResponseDto> findSeniorMatchingDetailByMatchingId(Long matchingId) {
        QMatching matching = QMatching.matching;
        QSenior senior = QSenior.senior;
        QSeniorDisease seniorDisease = QSeniorDisease.seniorDisease;
        QDiseaseDementiaMapping dementiaMapping = QDiseaseDementiaMapping.diseaseDementiaMapping;
        QMatchingCareTime careTime = QMatchingCareTime.matchingCareTime;
        QMatchingCareDetail careDetail = QMatchingCareDetail.matchingCareDetail;

        // 1️⃣ Senior & Matching 기본 정보 조회
        SeniorMatchingDetailResponseDto seniorDetail = queryFactory
                .select(Projections.constructor(SeniorMatchingDetailResponseDto.class,
                        senior.name,
                        senior.birthDate,
                        senior.gender.stringValue(),
                        senior.age,
                        senior.weight,
                        matching.matchingCondition.benefits,
                        senior.residence,
                        senior.gradeType.stringValue(), // ✅ Enum을 String으로 변환
                        senior.residenceType.stringValue(), // ✅ Enum을 String으로 변환
                        senior.profileImageUrl,
                        senior.staffContact
                ))
                .from(matching)
                .leftJoin(matching.senior, senior)
                .leftJoin(senior.seniorDisease, seniorDisease)
                .where(matching.id.eq(matchingId))
                .fetchOne();

        if (seniorDetail == null) {
            return Optional.empty();
        }

        // 2️⃣ careTimes 리스트 조회
        List<MatchingCareTimeResponseDto> careTimes = queryFactory
                .select(Projections.constructor(MatchingCareTimeResponseDto.class,
                        careTime.dayOfWeek,
                        careTime.startTime,
                        careTime.endTime
                ))
                .from(careTime)
                .where(careTime.matching.id.eq(matchingId))
                .fetch();

        // 3️⃣ careDetails 리스트 조회 (SeniorCareDetail Enum 변환)
        List<SeniorCareDetail> careDetailEnums = queryFactory
                .select(careDetail.careDetail)
                .from(careDetail)
                .where(careDetail.matching.id.eq(matchingId))
                .fetch();

        List<String> careDetails = careDetailEnums.stream()
                .map(SeniorCareDetail::getDescription) // ✅ Enum → 한글 변환
                .toList();

        // 4️⃣ dementiaSymptoms 리스트 조회 (DementiaSymptom Enum 변환)
        List<DementiaSymptom> dementiaSymptomEnums = queryFactory
                .select(dementiaMapping.dementiaSymptom)
                .from(dementiaMapping)
                .where(dementiaMapping.seniorDisease.id.eq(seniorDisease.id))
                .fetch();

        List<String> dementiaSymptoms = dementiaSymptomEnums.stream()
                .map(DementiaSymptom::getDescription) // ✅ Enum → 한글 변환
                .distinct() // 중복 제거
                .toList();

        // 6️⃣ 변환된 한글 값 적용
        seniorDetail = new SeniorMatchingDetailResponseDto(
                seniorDetail.getName(),
                seniorDetail.getBirthDate(),
                seniorDetail.getGender() != null
                        ? Gender.valueOf(seniorDetail.getGender()).getDescription()
                        : null,
                seniorDetail.getAge(),
                seniorDetail.getWeight(),
                seniorDetail.getBenefits(),
                seniorDetail.getResidence(),
                seniorDetail.getGradeType() != null
                        ? GradeType.valueOf(seniorDetail.getGradeType()).getDescription()  // ✅ Enum 변환 후 한글 반환
                        : null,
                seniorDetail.getResidenceType() != null
                        ? ResidenceType.valueOf(seniorDetail.getResidenceType()).getDescription()  // ✅ Enum 변환 후 한글 반환
                        : null,
                seniorDetail.getProfileImageUrl(),
                seniorDetail.getStaffContact()
        );

        // 7️⃣ DTO에 리스트 추가
        seniorDetail.updateCareTimes(careTimes);
        seniorDetail.updateCareDetails(careDetails);
        seniorDetail.updateDementiaSymptoms(dementiaSymptoms);

        return Optional.of(seniorDetail);
    }
}
