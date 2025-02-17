package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.senior.entity.Senior;
import com.ongi.backend.domain.senior.entity.SeniorDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeniorDiseaseRepository extends JpaRepository<SeniorDisease, Long> {
    Optional<SeniorDisease> findBySenior(Senior senior);
}
