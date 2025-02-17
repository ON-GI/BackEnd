package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.senior.entity.Senior;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeniorRepository extends JpaRepository<Senior, Long>, SeniorRepositoryCustom{

    @EntityGraph(attributePaths = {
            "careCondition", "careCondition.careTypes", "careCondition.seniorCareTimes",
            "seniorDisease", "seniorDisease.dementiaMappings"
    })
    Optional<Senior> findById(Long seniorId);
}
