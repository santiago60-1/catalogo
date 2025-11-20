package com.tiquets.catalogo.infraestructure.adapters.out;

import com.tiquets.catalogo.domain.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEventRepository extends JpaRepository<Events, String> {
    List<Events> findByName(String name);

}