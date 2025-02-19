package com.ongi.backend.domain.matching.repository;

import com.ongi.backend.common.enums.Gender;
import com.ongi.backend.domain.caregiver.entity.*;
import com.ongi.backend.domain.matching.dto.response.*;
import com.ongi.backend.domain.matching.entity.MatchingCareTime;
import com.ongi.backend.domain.matching.entity.QMatching;
import com.ongi.backend.domain.matching.entity.QMatchingCareDetail;
import com.ongi.backend.domain.matching.entity.QMatchingCareTime;
import com.ongi.backend.domain.matching.entity.enums.MatchingStatus;
import com.ongi.backend.domain.senior.entity.*;
import com.ongi.backend.domain.senior.entity.enums.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    public List<CaregiverMatchingResponseDto> findMatchingCaregivers(Long seniorId) {
        Senior senior = findSeniorById(seniorId);

        // ✅ 1. 모든 보호사를 가져오기 (필터링 제거)
        List<Caregiver> caregivers = getAllCaregivers();

        log.info("전체 보호사 수: {}", caregivers.size());

        // ✅ 2. 점수 계산
        return caregivers.stream()
                .map(caregiver -> CaregiverMatchingResponseDto.fromEntity(
                        caregiver.getId(),
                        maskName(caregiver.getName()),
                        getMatchingWorkTimes(senior, caregiver),
                        caregiver.getWorkCondition() != null ? caregiver.getWorkCondition().getPayAmount() : Integer.MAX_VALUE, // ✅ 근무 조건 없으면 최대값 설정
                        caregiver.getCaregiverInformation() != null
                                ? caregiver.getCaregiverInformation().getLicenses().stream()
                                .map(license -> license.getLicenseName().getDescription())
                                .collect(Collectors.toList())
                                : List.of(), // ✅ `null`이면 빈 리스트 반환
                        getMatchingRegions(senior, caregiver),
                        caregiver.getCaregiverInformation() != null && caregiver.getCaregiverInformation().isHasDementiaTraining(),
                        calculateMatchingScore(senior, caregiver)
                ))
                .filter(dto -> dto.getScore() > 0.0)
                .sorted((a, b) -> {
                    // ✅ 1. 어르신 등급이 GRADE_5 or GRADE_SUPPORT일 때 치매 교육 여부 먼저 비교
                    if (isCognitiveSupport(senior)) {
                        if (b.getHasDementiaTraining() != a.getHasDementiaTraining()) {
                            return Boolean.compare(b.getHasDementiaTraining(), a.getHasDementiaTraining());
                        }
                    }

                    // ✅ 2. 매칭 점수 기준으로 내림차순 정렬 (큰 값이 먼저 오도록)
                    int scoreComparison = Double.compare(b.getScore(), a.getScore());
                    if (scoreComparison != 0) {
                        return scoreComparison;
                    }

                    // ✅ 3. 시급은 기본적으로 오름차순 정렬 (낮은 급여가 먼저 오도록)
                    return Integer.compare(a.getPayAmount(), b.getPayAmount());
                })
                .collect(Collectors.toList());
    }

    // ✅ 모든 보호사 가져오기 (필터링 제거)
    private List<Caregiver> getAllCaregivers() {
        QCaregiver caregiver = QCaregiver.caregiver;
        return queryFactory
                .selectFrom(caregiver)
                .fetch();
    }

    // ✅ 2. 지역 일치율 계산
    private double calculateRegionMatchScore(Senior senior, Caregiver caregiver) {
        if (caregiver.getWorkCondition() == null || caregiver.getWorkCondition().getCaregiverWorkRegions().isEmpty()) {
            return 0.0; // ✅ 근무 지역 정보가 없으면 0점 처리
        }

        long matchingCount = caregiver.getWorkCondition().getCaregiverWorkRegions().stream()
                .filter(region -> isRegionMatching(senior.getResidence(), region))
                .count();
        log.info("지역 일치 수: {}", matchingCount);

        return (double) matchingCount / caregiver.getWorkCondition().getCaregiverWorkRegions().size();
    }

    private boolean isRegionMatching(Residence seniorResidence, CaregiverWorkRegion caregiverRegion) {
        if (!caregiverRegion.getCity().equals(seniorResidence.getCity())) return false;

        boolean districtMatches = caregiverRegion.getDistrict() == null
                || caregiverRegion.getDistrict().equals(seniorResidence.getDistrict());

        boolean townMatches = caregiverRegion.getTown() == null
                || caregiverRegion.getTown().equals(seniorResidence.getTown());

        return districtMatches && townMatches;
    }

    // ✅ 3. 시간 일치율 계산
    private double calculateTimeMatchScore(Senior senior, Caregiver caregiver) {
        if (caregiver.getWorkCondition() == null || caregiver.getWorkCondition().getCaregiverWorkTimes().isEmpty()) {
            log.warn("시간 일치율 계산 불가: 보호사의 근무 시간이 없음");
            return 0.0; // ✅ 근무 시간이 없으면 0점 처리
        }

        long totalSeniorTimes = senior.getMatchingTimes().size();
        if (totalSeniorTimes == 0) {
            log.warn("시간 일치율 계산 불가: 어르신의 매칭 시간이 없음");
            return 0.0;
        }

        long matchingCount = senior.getMatchingTimes().stream()
                .filter(seniorTime -> caregiver.getWorkCondition().getCaregiverWorkTimes().stream()
                        .anyMatch(caregiverTime -> isTimeMatching(seniorTime, caregiverTime)))
                .count();

        log.info("시간 일치 율 : {}", (double) matchingCount / totalSeniorTimes);
        return (double) matchingCount / totalSeniorTimes;
    }

    private boolean isTimeMatching(SeniorMatchingTime seniorTime, CaregiverWorkTime caregiverTime) {
        return seniorTime.getDayOfWeek() == caregiverTime.getDayOfWeek() &&
                !seniorTime.getEndTime().isBefore(caregiverTime.getStartTime()) &&
                !seniorTime.getStartTime().isAfter(caregiverTime.getEndTime());
    }

    // ✅ 점수 계산 방식 개선
    private double calculateMatchingScore(Senior senior, Caregiver caregiver) {
        double timeMatch = calculateTimeMatchScore(senior, caregiver);
        double regionMatch = calculateRegionMatchScore(senior, caregiver);
        double dementiaTrainingScore = calculateDementiaTrainingScore(senior, caregiver);

        double finalScore = (timeMatch * 0.4) + (regionMatch * 0.3) + (dementiaTrainingScore * 0.1);

        log.info("보호사 ID: {} | 최종 점수: {}", caregiver.getId(), finalScore);
        return finalScore;
    }

    // ✅ 치매 교육 여부 점수 추가
    private double calculateDementiaTrainingScore(Senior senior, Caregiver caregiver) {
        if (isCognitiveSupport(senior) && caregiver.getCaregiverInformation() != null) {
            return caregiver.getCaregiverInformation().isHasDementiaTraining() ? 1.0 : 0.0;
        }
        return 0.0;
    }

    // 이름 마스킹
    private String maskName(String name) {
        return name.charAt(0) + "O" + name.substring(2);
    }

    // ✅ 6. 어르신이 5등급 or 인지 지원 등급인지 확인
    private boolean isCognitiveSupport(Senior senior) {
        return senior.getGradeType() == GradeType.GRADE_5 || senior.getGradeType() == GradeType.GRADE_SUPPORT;
    }

    // ✅ 7. 보호사와 어르신의 일치하는 근무 시간 반환
    private List<CaregiverWorkTimeResponseDto> getMatchingWorkTimes(Senior senior, Caregiver caregiver) {
        if (caregiver.getWorkCondition() == null) {
            return List.of(); // ✅ 근무 조건이 없으면 빈 리스트 반환
        }

        return caregiver.getWorkCondition().getCaregiverWorkTimes().stream()
                .filter(caregiverTime -> senior.getMatchingTimes().stream()
                        .anyMatch(seniorTime -> isTimeMatching(seniorTime, caregiverTime)))
                .map(caregiverWorkTime ->
                        CaregiverWorkTimeResponseDto.fromEntity(
                                caregiverWorkTime.getDayOfWeek(),
                                caregiverWorkTime.getStartTime(),
                                caregiverWorkTime.getEndTime()))  // DTO로 변환
                .collect(Collectors.toList());
    }

    // ✅ 8. 보호사와 어르신의 일치하는 지역 반환
    private String getMatchingRegions(Senior senior, Caregiver caregiver) {
        if (caregiver.getWorkCondition() == null) {
            return ""; // ✅ 근무 조건이 없으면 빈 문자열 반환
        }

        return caregiver.getWorkCondition().getCaregiverWorkRegions().stream()
                .filter(region -> isRegionMatching(senior.getResidence(), region))
                .map(region -> region.getCity() + " " + (region.getDistrict() != null ? region.getDistrict() : "") +
                        " " + (region.getTown() != null ? region.getTown() : ""))
                .collect(Collectors.joining(", "));
    }

    private Senior findSeniorById(Long seniorId) {
        return queryFactory
                .selectFrom(QSenior.senior)
                .where(QSenior.senior.id.eq(seniorId))
                .fetchOne();
    }
}
