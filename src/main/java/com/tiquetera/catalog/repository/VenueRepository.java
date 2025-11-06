package com.tiquetera.catalog.repository;

import com.tiquetera.catalog.model.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<VenueEntity, Long> {
    
}
