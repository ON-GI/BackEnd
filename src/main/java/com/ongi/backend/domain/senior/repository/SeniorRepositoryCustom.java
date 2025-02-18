package com.ongi.backend.domain.senior.repository;

import com.ongi.backend.domain.center.entity.Center;
import com.ongi.backend.domain.senior.entity.Senior;

import java.util.List;

public interface SeniorRepositoryCustom {

    List<Senior> findSeniorsByCenter(Center center);
}
