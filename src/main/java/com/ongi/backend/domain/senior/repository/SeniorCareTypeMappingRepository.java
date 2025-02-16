package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.senior.entity.SeniorCareTypeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeniorCareTypeMappingRepository extends JpaRepository<SeniorCareTypeMapping, Long> {
}
