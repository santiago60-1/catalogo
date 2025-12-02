package com.ticket.catalogo.application.service;

import com.ticket.catalogo.application.port.out.EventoRespositoryPort;
import com.ticket.catalogo.application.port.out.VenueRepositoryPort;
import com.ticket.catalogo.domain.model.Evento;
import com.ticket.catalogo.domain.model.Venue;

import com.ticket.catalogo.shared.exceptions.DomainException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Caso de uso para operaciones sobre Evento.
 * - Validaciones de negocio
 * - Búsqueda con filtros básicos (delegar a infra para Specifications en grandes volúmenes)
 */
@Service
public class EventoUseCase {

    private final EventoRespositoryPort eventoRepository;
    private final VenueRepositoryPort venueRepository;

    public EventoUseCase(EventoRespositoryPort eventoRepository,
                         VenueRepositoryPort venueRepository) {
        this.eventoRepository = eventoRepository;
        this.venueRepository = venueRepository;
    }

    @Transactional
    public Evento crearEvento(Evento evento) {
        validarEventoParaCrear(evento);

        Long venueId = Optional.ofNullable(evento.getVenue())
                .map(Venue::getId)
                .orElseThrow(() -> new DomainException("El venue es obligatorio"));

        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new DomainException("Venue no encontrado con id: " + venueId));

        // Asociar venue verificado
        evento.setVenue(venue);

        // Reglas adicionales (ej. capacidad, solapamiento) pueden implementarse aquí o en un DomainService
        return eventoRepository.save(evento);
    }

    @Transactional
    public Evento actualizarEvento(Long id, Evento cambios) {
        if (id == null) throw new DomainException("Id de evento requerido para actualizar");

        Evento existente = eventoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Evento no encontrado con id: " + id));

        if (StringUtils.hasText(cambios.getNombre())) {
            existente.setNombre(cambios.getNombre());
        }
        if (cambios.getFechaInicio() != null) {
            existente.setFechaInicio(cambios.getFechaInicio());
        }
        if (cambios.getFechaFin() != null) {
            existente.setFechaFin(cambios.getFechaFin());
        }
        if (StringUtils.hasText(cambios.getEstado())) {
            existente.setEstado(cambios.getEstado());
        }
        if (cambios.getVenue() != null && cambios.getVenue().getId() != null) {
            Long newVenueId = cambios.getVenue().getId();
            Venue venue = venueRepository.findById(newVenueId)
                    .orElseThrow(() -> new DomainException("Venue no encontrado con id: " + newVenueId));
            existente.setVenue(venue);
        }

        validarFechas(existente.getFechaInicio(), existente.getFechaFin());

        return eventoRepository.save(existente);
    }

    @Transactional(readOnly = true)
    public Evento obtenerPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new DomainException("Evento no encontrado con id: " + id));
    }

    /**
     * Buscar eventos con filtros opcionales.
     * - Para volúmenes pequeños/medios: delega a repository.findAll() o findByVenueId() y filtra en memoria.
     * - Para volúmenes grandes: implementa en el puerto un método search(criteria) que use Specifications en infraestructura.
     */
    @Transactional(readOnly = true)
    public List<Evento> buscarEventos(String estado, LocalDateTime inicio, LocalDateTime fin, Long venueId) {
        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            throw new DomainException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        List<Evento> candidatos;
        if (venueId != null) {
            candidatos = eventoRepository.findByVenueId(venueId);
        } else {
            candidatos = eventoRepository.findAll();
        }

        return candidatos.stream()
                .filter(e -> filterByEstado(e, estado))
                .filter(e -> filterByFechaInicio(e, inicio))
                .filter(e -> filterByFechaFin(e, fin))
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarEvento(Long id) {
        if (id == null) {
            throw new DomainException("Id de evento es obligatorio para eliminar");
        }

        boolean existe = eventoRepository.findById(id).isPresent();
        if (!existe) {
            throw new DomainException("Evento no encontrado con id: " + id);
        }

        eventoRepository.deleteById(id);
    }


    /* -------------------------
       Validaciones privadas
       ------------------------- */
    private void validarEventoParaCrear(Evento evento) {
        if (evento == null) throw new DomainException("Evento no puede ser nulo");
        if (!StringUtils.hasText(evento.getNombre())) {
            throw new DomainException("El nombre del evento es obligatorio");
        }
        if (evento.getVenue() == null || evento.getVenue().getId() == null) {
            throw new DomainException("El venue (con id) es obligatorio");
        }
        validarFechas(evento.getFechaInicio(), evento.getFechaFin());
    }

    private void validarFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null) {
            return; // si tu regla exige ambas fechas, lanza excepción aquí
        }
        if (inicio.isAfter(fin)) {
            throw new DomainException("La fecha de inicio debe ser anterior o igual a la fecha fin");
        }
    }

    private boolean filterByEstado(Evento e, String estado) {
        if (!StringUtils.hasText(estado)) return true;
        return estado.equalsIgnoreCase(Optional.ofNullable(e.getEstado()).orElse(""));
    }

    private boolean filterByFechaInicio(Evento e, LocalDateTime inicio) {
        if (inicio == null) return true;
        LocalDateTime fechaInicioEvento = e.getFechaInicio();
        return fechaInicioEvento != null && !fechaInicioEvento.isBefore(inicio);
    }

    private boolean filterByFechaFin(Evento e, LocalDateTime fin) {
        if (fin == null) return true;
        LocalDateTime fechaFinEvento = e.getFechaFin();
        return fechaFinEvento != null && !fechaFinEvento.isAfter(fin);
    }
}
