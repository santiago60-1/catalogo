package com.ticket.catalogo.application.port.out;

import com.ticket.catalogo.domain.model.Evento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventoRespositoryPort {
    Evento save(Evento evento);
    Optional<Evento> findById(Long id);
    List<Evento> findAll();
    List<Evento> findByVenueId(Long venueId);
    void deleteById(Long id);

    Page<Evento> search(EventoSearchCriteria criteria, Pageable pageable);
}
