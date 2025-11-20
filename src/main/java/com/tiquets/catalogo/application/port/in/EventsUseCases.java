package com.tiquets.catalogo.application.port.in;

import com.tiquets.catalogo.domain.Events;

import java.util.List;
public interface EventsUseCases {

    public Events getEvents(String id);
    public List<Events> getEventsByName(String name);
    
    public void updateEvents(Events events);
    public void deleteEvents(String id);
}