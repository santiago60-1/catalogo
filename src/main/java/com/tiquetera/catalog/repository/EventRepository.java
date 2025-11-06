package com.tiquetera.catalog.repository;

import com.tiquetera.catalog.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long>, JpaSpecificationExecutor<EventEntity> {
    Optional<EventEntity> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
