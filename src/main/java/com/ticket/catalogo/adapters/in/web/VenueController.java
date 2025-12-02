package com.ticket.catalogo.adapters.in.web;

import com.ticket.catalogo.application.DTO.VenueDto;
import com.ticket.catalogo.application.service.VenueUseCase;
import com.ticket.catalogo.domain.model.Venue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueUseCase venueUseCase;

    public VenueController(VenueUseCase venueUseCase) {
        this.venueUseCase = venueUseCase;
    }

    // Crear venue
    @PostMapping
    public VenueDto crearVenue(@RequestBody VenueDto venueDto) {
        Venue v = venueDto.toDomain();
        Venue saved = venueUseCase.crearVenue(v);
        return VenueDto.fromDomain(saved);
    }

    // Actualizar venue
    @PutMapping("/{id}")
    public VenueDto actualizarVenue(@PathVariable Long id, @RequestBody VenueDto cambios) {
        Venue cambiosDomain = cambios.toDomain();
        Venue updated = venueUseCase.actualizarVenue(id, cambiosDomain);
        return VenueDto.fromDomain(updated);
    }

    // Obtener por id
    @GetMapping("/{id}")
    public VenueDto obtenerPorId(@PathVariable Long id) {
        return VenueDto.fromDomain(venueUseCase.obtenerPorId(id));
    }

    // Listar todos
    @GetMapping
    public List<VenueDto> listarTodos() {
        return VenueDto.fromDomainList(venueUseCase.listarTodos());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminarVenue(@PathVariable Long id) {
        venueUseCase.eliminarVenue(id);
    }
}
