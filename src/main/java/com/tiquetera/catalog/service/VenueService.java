package com.tiquetera.catalog.service;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.mapper.VenueMapper;
import com.tiquetera.catalog.model.Venue;
import com.tiquetera.catalog.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class VenueService {

    private final VenueRepository venueRepo;

    public VenueService (VenueRepository venueRepo) {
        this.venueRepo = venueRepo;
    }

    /**
     * Service for Venue business logic.
     *
     * Responsibilities:
     * - Persist and retrieve Venue domain objects.
     * - Convert between DTO and domain using VenueMapper.
     * - Throw NoSuchElementException when resources are missing.
     */

    public VenueDTO create(VenueDTO dto) {
        Venue v = VenueMapper.toDomain(dto);
        Venue saved = venueRepo.save(v);
        return VenueMapper.toDto(saved);
    }

    /**
     * Create a venue and return the saved DTO.
     */

    public List<VenueDTO> list() {
        return venueRepo.findAll().stream().map(VenueMapper::toDto).collect(Collectors.toList());
    }

    /**
     * List all venues as DTOs.
     */

    public VenueDTO getById(Long id) {
        Venue v = venueRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Venue not found"));
        return VenueMapper.toDto(v);
    }

    /**
     * Get a venue by id. Throws NoSuchElementException if not found.
     */

    public VenueDTO update(Long id, VenueDTO dto) {
        Venue existing = venueRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Venue not found"));
        dto.setId(existing.getId());
        Venue updated = venueRepo.save(VenueMapper.toDomain(dto));
        return VenueMapper.toDto(updated);
    }

    /**
     * Update an existing venue and return the updated DTO.
     */

    public void delete(Long id) {
        if (!venueRepo.findById(id).isPresent()) throw new NoSuchElementException("Venue not found");
        venueRepo.deleteById(id);
    }
}