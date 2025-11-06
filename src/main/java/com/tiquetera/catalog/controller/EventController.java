package com.tiquetera.catalog.controller;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Events management endpoints")
public class EventController {

    private final EventService service;

    public EventController(EventService service) { this.service = service; }

    @Operation(summary = "Create event", description = "Creates a new event associated with an existing venue")
    @ApiResponse(responseCode = "201", description = "Event created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventDTO dto) {
        EventDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/events/" + created.getId())).body(created);
    }

    @Operation(summary = "List events", description = "Get all events (no pagination)")
    @ApiResponse(responseCode = "200", description = "List of events retrieved")
    @GetMapping
    public ResponseEntity<List<EventDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Search events", description = "Search events with optional filters and pagination")
    @ApiResponse(responseCode = "200", description = "Page of events")
    @GetMapping("/search")
    public ResponseEntity<Page<EventDTO>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String from, // ISO_LOCAL_DATE_TIME optional
            Pageable pageable
    ) {
        LocalDateTime start = null;
        if (from != null && !from.isBlank()) {
            try {
                start = LocalDateTime.parse(from);
            } catch (DateTimeParseException ex) {
                return ResponseEntity.badRequest().build();
            }
        }
        Page<EventDTO> page = service.search(city, category, start, pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get event by id", description = "Get a single event by its id")
    @ApiResponse(responseCode = "200", description = "Event found")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Update event", description = "Update an existing event")
    @ApiResponse(responseCode = "200", description = "Event updated")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Delete event", description = "Delete an event by its id")
    @ApiResponse(responseCode = "204", description = "Event deleted")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
