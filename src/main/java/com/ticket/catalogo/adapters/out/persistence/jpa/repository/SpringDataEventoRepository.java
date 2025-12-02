package com.ticket.catalogo.adapters.out.persistence.jpa.repository;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaEventoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataEventoRepository
        extends JpaRepository<JpaEventoEntity, Long>, JpaSpecificationExecutor<JpaEventoEntity> {

    @EntityGraph(attributePaths = {"venue"})
    List<JpaEventoEntity> findAll();

    @Query("SELECT e FROM JpaEventoEntity e JOIN FETCH e.venue WHERE e.estado = :estado")
    List<JpaEventoEntity> findEventosConVenuePorEstado(@Param("estado") String estado);

    List<JpaEventoEntity> findByVenueId(Long venueId);
}
