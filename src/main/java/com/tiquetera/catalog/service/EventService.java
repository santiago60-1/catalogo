package com.tiquetera.catalog.service;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.mapper.EventMapper;
import com.tiquetera.catalog.model.Event;
import com.tiquetera.catalog.repository.EventRepository;
import com.tiquetera.catalog.repository.VenueRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;

    public EventService(EventRepository eventRepo, VenueRepository venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    /**
     * Create a new event.
     *
     * Validates that the referenced venue exists before saving. If the
     * venue is missing, a NoSuchElementException is thrown and mapped to 404 by
     * the global exception handler.
     */
    public EventDTO create(EventDTO dto) {
        if (!venueRepo.findById(dto.getVenueId()).isPresent()) {
            throw new NoSuchElementException("Venue not found for id " + dto.getVenueId());
        }
        Event saved = eventRepo.save(EventMapper.toDomain(dto));
        return EventMapper.toDto(saved);
    }

    public List<EventDTO> list() {
        return eventRepo.findAll().stream().map(EventMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Retrieve all events as DTOs.
     */

    public EventDTO getById(Long id) {
        Event e = eventRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Event not found"));
        return EventMapper.toDto(e);
    }

    /**
     * Get a single event by id.
     *
     * Throws NoSuchElementException if not found.
     */

    public EventDTO update(Long id, EventDTO dto) {
        Event existing = eventRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Event not found"));
        dto.setId(existing.getId());
        if (!venueRepo.findById(dto.getVenueId()).isPresent()) {
            throw new NoSuchElementException("Venue not found for id " + dto.getVenueId());
        }
        Event updated = eventRepo.save(EventMapper.toDomain(dto));
        return EventMapper.toDto(updated);
    }

    public void delete(Long id) {
        if (!eventRepo.findById(id).isPresent()) throw new NoSuchElementException("Event not found");
        eventRepo.deleteById(id);
    }
}
