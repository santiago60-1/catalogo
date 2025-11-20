package com.tiquets.catalogo.application.port.out;

import com.tiquets.catalogo.domain.Events;

import java.util.List;
public interface EventRepositoryPort {

    Events getAll();
    Events findById(String id);
    List<Events> findByName(String name);
    Events save(Events events);
    Events update(Events events);
    void deleteById(String id);
}
