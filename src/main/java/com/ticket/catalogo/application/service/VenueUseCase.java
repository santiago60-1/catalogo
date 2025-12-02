package com.ticket.catalogo.application.service;

import com.ticket.catalogo.application.port.out.VenueRepositoryPort;
import com.ticket.catalogo.domain.model.Venue;

import com.ticket.catalogo.shared.exceptions.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso para operaciones sobre Venue.
 * - Valida reglas de negocio simples
 * - Orquesta persistencia a través del puerto VenueRepositoryPort
 */
@Service
public class VenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public VenueUseCase(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Transactional
    public Venue crearVenue(Venue venue) {
        validarVenueParaCrear(venue);
        return venueRepository.save(venue);
    }

    @Transactional
    public Venue actualizarVenue(Long id, Venue cambios) {
        if (id == null) throw new DomainException("Id de venue requerido para actualizar");

        Venue existente = venueRepository.findById(id)
                .orElseThrow(() -> new DomainException("Venue no encontrado con id: " + id));

        if (StringUtils.hasText(cambios.getNombre())) {
            existente.setNombre(cambios.getNombre());
        }
        if (cambios.getCapacidad() != null) {
            if (cambios.getCapacidad() < 0) throw new DomainException("Capacidad inválida");
            existente.setCapacidad(cambios.getCapacidad());
        }
        if (StringUtils.hasText(cambios.getUbicacion())) {
            existente.setUbicacion(cambios.getUbicacion());
        }

        return venueRepository.save(existente);
    }

    @Transactional(readOnly = true)
    public Venue obtenerPorId(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new DomainException("Venue no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Venue> listarTodos() {
        return venueRepository.findAll();
    }

    @Transactional
    public void eliminarVenue(Long id) {
        // Validación: si existen eventos asociados, la regla puede ser impedir borrado o permitir cascade.
        // Aquí delegamos la regla a la infraestructura o asumimos que la BD maneja ON DELETE CASCADE.
        if (!venueRepository.findById(id).isPresent()) {
            throw new DomainException("Venue no encontrado con id: " + id);
        }
        venueRepository.deleteById(id);
    }

    /* -------------------------
       Validaciones privadas
       ------------------------- */
    private void validarVenueParaCrear(Venue venue) {
        if (venue == null) throw new DomainException("Venue no puede ser nulo");
        if (!StringUtils.hasText(venue.getNombre())) {
            throw new DomainException("El nombre del venue es obligatorio");
        }
        if (venue.getCapacidad() != null && venue.getCapacidad() < 0) {
            throw new DomainException("La capacidad debe ser un número positivo");
        }
    }
}
