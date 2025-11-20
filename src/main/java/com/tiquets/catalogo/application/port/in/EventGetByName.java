package com.tiquets.catalogo.application.port.in;

import com.tiquets.catalogo.domain.Events;

import java.util.List;

public interface EventGetByName {
    public List<Events> getEventsByName(String name);

}
