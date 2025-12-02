package com.ticket.catalogo.adapters.in.web;

import com.ticket.catalogo.application.DTO.EventoDto;
import com.ticket.catalogo.application.service.EventoUseCase;
import com.ticket.catalogo.domain.model.Evento;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoUseCase eventoUseCase;

    public EventoController(EventoUseCase eventoUseCase) {
        this.eventoUseCase = eventoUseCase;
    }

    // Crear evento
    @PostMapping
    public EventoDto crearEvento(@RequestBody EventoDto eventoDto) {
        Evento eDomain = eventoDto.toDomain();
        Evento created = eventoUseCase.crearEvento(eDomain);
        return EventoDto.fromDomain(created);
    }

    // Actualizar evento
    @PutMapping("/{id}")
    public EventoDto actualizarEvento(@PathVariable Long id, @RequestBody EventoDto cambios) {
        Evento cambiosDomain = cambios.toDomain();
        Evento updated = eventoUseCase.actualizarEvento(id, cambiosDomain);
        return EventoDto.fromDomain(updated);
    }

    // Obtener evento por ID
    @GetMapping("/{id}")
    public EventoDto obtenerPorId(@PathVariable Long id) {
        return EventoDto.fromDomain(eventoUseCase.obtenerPorId(id));
    }

    // Buscar eventos con filtros opcionales
    @GetMapping("/search")
    public List<EventoDto> buscarEventos(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) LocalDateTime inicio,
            @RequestParam(required = false) LocalDateTime fin,
            @RequestParam(required = false) Long venueId) {
        List<Evento> resultados = eventoUseCase.buscarEventos(estado, inicio, fin, venueId);
        return resultados.stream().map(EventoDto::fromDomain).collect(Collectors.toList());
    }

    // Eliminar evento
    @DeleteMapping("/{id}")
    public void eliminarEvento(@PathVariable Long id) {
        eventoUseCase.eliminarEvento(id);
    }
}
