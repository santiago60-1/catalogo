package com.tiquetera.catalog.controller;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Events management endpoints")
public class EventController {
    private final EventService service;

    public EventController(EventService service) { this.service = service; }

    /**
     * Controller for Event resources.
     *
     * Notes:
     * - Endpoints use DTO objects for input/output.
     * - Validation is performed using Jakarta Validation annotations on DTOs.
     * - Service layer throws NoSuchElementException when resources are missing; the
     *   global exception handler maps those to 404 responses.
     */

    @Operation(summary = "Create event", description = "Creates a new event associated with an existing venue")
    @ApiResponse(responseCode = "201", description = "Event created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventDTO dto) {
        EventDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/events/" + created.getId())).body(created);
    }

    /**
     * Create an event.
     *
     * Input: validated EventDTO (requires name and venueId).
     * Returns: 201 Created with Location header pointing to the new resource.
     */

    @Operation(summary = "List events", description = "Get all events")
    @ApiResponse(responseCode = "200", description = "List of events retrieved")
    @GetMapping
    public ResponseEntity<List<EventDTO>> list() { return ResponseEntity.ok(service.list()); }

    /**
     * List all events.
     *
     * Returns: 200 OK with a JSON array of EventDTO.
     */

    @Operation(summary = "Get event by id", description = "Get a single event by its id")
    @ApiResponse(responseCode = "200", description = "Event found")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> get(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }

    /**
     * Get a single event by id.
     *
     * Returns: 200 OK with EventDTO or 404 Not Found if the id does not exist.
     */

    @Operation(summary = "Update event", description = "Update an existing event")
    @ApiResponse(responseCode = "200", description = "Event updated")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Update an existing event.
     *
     * Returns: 200 OK with updated EventDTO or 404 Not Found if the id does not exist.
     */

    @Operation(summary = "Delete event", description = "Delete an event by its id")
    @ApiResponse(responseCode = "204", description = "Event deleted")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete an event by id.
     *
     * Returns: 204 No Content on success, 404 Not Found if the id does not exist.
     */
}