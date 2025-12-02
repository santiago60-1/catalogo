package com.ticket.catalogo.application.port.out;

import com.ticket.catalogo.domain.model.Venue;

import java.util.List;
import java.util.Optional;

public interface VenueRepositoryPort {

    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    List<Venue> findAll();
    List<Venue> findByVenueId(Long venueId);
    void deleteById(Long id);

}
