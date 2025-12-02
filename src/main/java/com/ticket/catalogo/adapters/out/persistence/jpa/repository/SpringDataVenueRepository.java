package com.ticket.catalogo.adapters.out.persistence.jpa.repository;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaVenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataVenueRepository extends JpaRepository<JpaVenueEntity, Long> {
    // Aquí puedes agregar métodos de consulta personalizados si los necesitas, por ejemplo:
    // List<JpaVenueEntity> findByNombreContainingIgnoreCase(String nombre);
}
