package com.tiquetera.catalog.service;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.exception.NotFoundException;
import com.tiquetera.catalog.mapper.VenueMapper;
import com.tiquetera.catalog.model.VenueEntity;
import com.tiquetera.catalog.repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    private final VenueRepository venueRepo;

    public VenueService(VenueRepository venueRepo) {
        this.venueRepo = venueRepo;
    }

    /**
     * Crea un venue y devuelve el DTO persistido.
     */
    @Transactional
    public VenueDTO create(VenueDTO dto) {
        VenueEntity v = VenueMapper.toDomain(dto);
        VenueEntity saved = venueRepo.save(v);
        return VenueMapper.toDto(saved);
    }

    /**
     * Lista todos los venues como DTOs.
     * Se deja readOnly=true por rendimiento.
     */
    @Transactional(readOnly = true)
    public List<VenueDTO> list() {
        return venueRepo.findAll().stream().map(VenueMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Obtiene un venue por id; lanza NotFoundException si no existe.
     */
    @Transactional(readOnly = true)
    public VenueDTO getById(Long id) {
        VenueEntity v = venueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue not found: " + id));
        return VenueMapper.toDto(v);
    }

    /**
     * Actualiza un venue existente y devuelve el DTO actualizado.
     * Lanza NotFoundException si el venue no existe.
     */
    @Transactional
    public VenueDTO update(Long id, VenueDTO dto) {
        VenueEntity existing = venueRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Venue not found: " + id));
        dto.setId(existing.getId());
        VenueEntity toSave = VenueMapper.toDomain(dto);
        toSave.setId(existing.getId());
        VenueEntity saved = venueRepo.save(toSave);
        return VenueMapper.toDto(saved);
    }

    /**
     * Elimina un venue por id; lanza NotFoundException si no existe.
     */
    @Transactional
    public void delete(Long id) {
        if (!venueRepo.existsById(id)) {
            throw new NotFoundException("Venue not found: " + id);
        }
        venueRepo.deleteById(id);
    }
}
