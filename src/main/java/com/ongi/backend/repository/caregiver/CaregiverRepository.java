package com.ongi.backend.repository.caregiver;

import com.ongi.backend.entity.caregiver.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long>, CaregiverRepositoryCustom {
}
