package com.ongi.backend.domain.caregiver.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class CaregiverRepositoryImpl implements CaregiverRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CaregiverRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
