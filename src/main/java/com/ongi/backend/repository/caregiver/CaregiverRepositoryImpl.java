package com.ongi.backend.repository.caregiver;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class CaregiverRepositoryImpl implements CaregiverRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CaregiverRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
