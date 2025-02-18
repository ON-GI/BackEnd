package com.ongi.backend.domain.center.repository;

import com.ongi.backend.domain.center.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CenterRepository extends JpaRepository<Center, Long> {

    Optional<Center> findByCenterCode(String centerCode);
    List<Center> findTop10ByNameStartingWith(String name);

    boolean existsByCenterCode(String centerCode);
}
