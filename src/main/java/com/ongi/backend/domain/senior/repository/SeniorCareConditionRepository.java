package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.SeniorCareCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeniorCareConditionRepository extends JpaRepository<SeniorCareCondition, Long> {

    Optional<SeniorCareCondition> findBySenior(Senior senior);
}
