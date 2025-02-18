package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeniorRepositoryImpl implements SeniorRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public SeniorRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Senior> findSeniorsByCenter(Center center){
        QSenior senior = QSenior.senior;
        QSeniorCareCondition careCondition = QSeniorCareCondition.seniorCareCondition;
        QSeniorCareTypeMapping careTypeMapping = QSeniorCareTypeMapping.seniorCareTypeMapping;
        QSeniorCareTime seniorCareTime = QSeniorCareTime.seniorCareTime;
        QSeniorDisease seniorDisease = QSeniorDisease.seniorDisease;
        QDiseaseDementiaMapping dementiaMapping = QDiseaseDementiaMapping.diseaseDementiaMapping;

        // ✅ Step 1: Senior 기본 정보 + careCondition 만 가져오기
        List<Senior> seniors = queryFactory
                .selectFrom(senior)
                .leftJoin(senior.careCondition, careCondition).fetchJoin()
                .leftJoin(senior.seniorDisease, seniorDisease).fetchJoin()
                .where(senior.center.eq(center))
                .fetch();

        // ✅ Step 2: careTypes와 seniorCareTimes를 별도로 조회
        Map<Long, List<SeniorCareTypeMapping>> careTypeMap = queryFactory
                .selectFrom(careTypeMapping)
                .where(careTypeMapping.seniorCareCondition.senior.in(seniors))
                .stream().collect(Collectors.groupingBy(c -> c.getSeniorCareCondition().getSenior().getId()));

        Map<Long, List<SeniorCareTime>> careTimeMap = queryFactory
                .selectFrom(seniorCareTime)
                .where(seniorCareTime.careCondition.senior.in(seniors))
                .stream().collect(Collectors.groupingBy(t -> t.getCareCondition().getSenior().getId()));

        // ✅ Step 3: 기존 Senior 객체에 데이터 매핑
        seniors.forEach(seniorEntity -> {
            SeniorCareCondition careConditionEntity = seniorEntity.getCareCondition();
            if (careConditionEntity != null) {
                careConditionEntity.getCareTypes().addAll(careTypeMap.getOrDefault(seniorEntity.getId(), new ArrayList<>()));
                careConditionEntity.getSeniorCareTimes().addAll(careTimeMap.getOrDefault(seniorEntity.getId(), new ArrayList<>()));
            }
        });

        return seniors;
    }
}
