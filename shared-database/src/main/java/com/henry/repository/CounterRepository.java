package com.henry.repository;

import com.henry.domain.entity.CounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CounterRepository extends JpaRepository<CounterEntity, String> {
    Optional<CounterEntity> findByCode(String code);

    boolean existsByCode(String code);
}
