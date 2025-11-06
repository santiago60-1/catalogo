package com.tiquetera.catalog.controller;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/venues")
@Tag(name = "Venues", description = "Venue management endpoints")
public class VenueController {

    private final VenueService service;

    public VenueController(VenueService service) { this.service = service; }

    @Operation(summary = "Create venue", description = "Create a new venue")
    @PostMapping
    public ResponseEntity<VenueDTO> create(@Valid @RequestBody VenueDTO dto) {
        VenueDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/venues/" + created.getId())).body(created);
    }

    @Operation(summary = "List venues", description = "Get all venues")
    @GetMapping
    public ResponseEntity<List<VenueDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @Operation(summary = "Get venue by id", description = "Get venue by id")
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update venue", description = "Update an existing venue")
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> update(@PathVariable Long id, @Valid @RequestBody VenueDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete venue", description = "Delete a venue by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
