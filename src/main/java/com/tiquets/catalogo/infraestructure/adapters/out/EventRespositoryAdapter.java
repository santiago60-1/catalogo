package com.tiquets.catalogo.infraestructure.adapters.out;

import com.tiquets.catalogo.application.port.in.*;
import com.tiquets.catalogo.application.port.out.EventRepositoryPort;
import com.tiquets.catalogo.domain.Events;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRespositoryAdapter implements EventRepositoryPort {


    private final JpaEventRepository jpaEventRepository;

    public EventRespositoryAdapter(JpaEventRepository jpaEventRepository){
        this.jpaEventRepository=jpaEventRepository;
    }

    @Override
    public Events getAll(){
        return jpaEventRepository.findAll().stream().findFirst().orElse(null);
    }

    @Override
    public Events findById(String id){
        return jpaEventRepository.findById(id).orElse(null);
    }

    @Override
    public List<Events>findByName(String name){
        return jpaEventRepository.findByName(name);
    }

    @Override
    public Events save(Events events){
        return jpaEventRepository.save(events);
    }

    @Override
    public Events update(Events events){
        return jpaEventRepository.save(events);
    }

    @Override
    public void deleteById(String id){
        jpaEventRepository.deleteById(id);
    }
}