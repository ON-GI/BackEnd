package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.senior.entity.SeniorDisease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorDiseaseRepository extends JpaRepository<SeniorDisease, Long> {
}
