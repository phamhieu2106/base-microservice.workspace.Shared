package com.henry.base.repository;

import com.henry.domain.entity.HistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, String> {

    Page<HistoryEntity> findAllByEntityId(String entityId, Pageable pageable);
}
