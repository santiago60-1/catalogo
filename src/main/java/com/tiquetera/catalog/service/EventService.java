package com.tiquetera.catalog.service;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.exception.DuplicateResourceException;
import com.tiquetera.catalog.exception.NotFoundException;
import com.tiquetera.catalog.mapper.EventMapper;
import com.tiquetera.catalog.model.EventEntity;
import com.tiquetera.catalog.model.VenueEntity;
import com.tiquetera.catalog.repository.EventRepository;
import com.tiquetera.catalog.repository.VenueRepository;
import com.tiquetera.catalog.repository.spec.EventSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final VenueRepository venueRepo;

    public EventService(EventRepository eventRepo, VenueRepository venueRepo) {
        this.eventRepo = eventRepo;
        this.venueRepo = venueRepo;
    }

    @Transactional
    public EventDTO create(EventDTO dto) {
        // Validar duplicado por nombre (case-insensitive)
        if (dto.getName() != null && eventRepo.existsByNameIgnoreCase(dto.getName().trim())) {
            throw new DuplicateResourceException("Ya existe un evento con el nombre: " + dto.getName());
        }

        VenueEntity venue = venueRepo.findById(dto.getVenueId())
                .orElseThrow(() -> new NotFoundException("Venue no encontrado para id: " + dto.getVenueId()));

        EventEntity toSave = EventMapper.toDomain(dto);
        toSave.setVenue(venue);

        EventEntity saved = eventRepo.save(toSave);
        return EventMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public List<EventDTO> listAll() {
        return eventRepo.findAll().stream().map(EventMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventDTO getById(Long id) {
        EventEntity e = eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado: " + id));
        return EventMapper.toDto(e);
    }

    @Transactional
    public EventDTO update(Long id, EventDTO dto) {
        EventEntity existing = eventRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Evento no encontrado: " + id));

        String newName = dto.getName() == null ? existing.getName() : dto.getName().trim();
        if (!existing.getName().equalsIgnoreCase(newName) && eventRepo.existsByNameIgnoreCase(newName)) {
            throw new DuplicateResourceException("Ya existe un evento con el nombre: " + newName);
        }

        VenueEntity venue = venueRepo.findById(dto.getVenueId())
                .orElseThrow(() -> new NotFoundException("Venue no encontrado para id: " + dto.getVenueId()));

        EventEntity toUpdate = EventMapper.toDomain(dto);
        toUpdate.setId(existing.getId());
        toUpdate.setVenue(venue);

        EventEntity saved = eventRepo.save(toUpdate);
        return EventMapper.toDto(saved);
    }

        /**
     * Backward-compatible alias used by controllers/tests:
     * Returns all events as DTO list.
     */
    @Transactional(readOnly = true)
    public List<EventDTO> list() {
        return listAll();
    }

    @Transactional
    public void delete(Long id) {
        if (!eventRepo.existsById(id)) {
            throw new NotFoundException("Evento no encontrado: " + id);
        }
        eventRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<EventDTO> search(String city, String category, LocalDateTime startDate, Pageable pageable) {
        Specification<EventEntity> spec = Specification
                .where(EventSpecifications.cityContains(city))
                .and(EventSpecifications.categoryContains(category))
                .and(EventSpecifications.startDateAfterOrEqual(startDate));

        Page<EventEntity> page = eventRepo.findAll(spec, pageable);
        return page.map(EventMapper::toDto);
    }
}
