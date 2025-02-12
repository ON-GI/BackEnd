package com.ongi.backend.domain.caregiver.repository;

import com.ongi.backend.domain.caregiver.entity.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long>, CaregiverRepositoryCustom {
}
