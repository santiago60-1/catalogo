package com.tiquets.catalogo.application.service;

import com.tiquets.catalogo.application.port.in.*;
import com.tiquets.catalogo.application.port.out.EventRepositoryPort;
import com.tiquets.catalogo.domain.Events;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService implements EventGetUseCases, EventFindById ,EventGetByName,EventCreateUseCase ,EventUpdateUseCase, EventDeleteUseCase {

    private final EventRepositoryPort eventRepositoryPort;

    public EventService(EventRepositoryPort eventRepositoryPort)
    {
        this.eventRepositoryPort = eventRepositoryPort;

    }

    @Override
    public Events getEvents(){
        return eventRepositoryPort.getAll();
    }

    @Override
    public Events findById(String id){
        return eventRepositoryPort.findById(id);
    }

    @Override
    public List<Events> getEventsByName(String name){
        return eventRepositoryPort.findByName(name);
    }

    @Override
    public Events createEvents(Events events){
        return eventRepositoryPort.save(events);
    }

    @Override
    public Events updateEvents(Events events){
        return eventRepositoryPort.update(events);
    }

    @Override
    public void deleteEvents(String id){
         eventRepositoryPort.deleteById(id);
    }

}
