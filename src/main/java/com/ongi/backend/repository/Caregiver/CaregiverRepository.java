package com.ongi.backend.repository.Caregiver;

import com.ongi.backend.entity.Caregiver.Caregiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaregiverRepository extends JpaRepository<Caregiver, Long>, CaregiverRepositoryCustom {
}
