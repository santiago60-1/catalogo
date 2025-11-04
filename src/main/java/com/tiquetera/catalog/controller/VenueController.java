package com.tiquetera.catalog.controller;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/venues")
@Tag(name = "Venues", description = "Venues management endpoints")
public class VenueController {

    private final VenueService service;

    public VenueController(VenueService service) {
        this.service = service;
    }

    /**
     * Controller for Venue resources.
     *
     * Responsibilities:
     * - Accept and validate request payloads (VenueDTO).
     * - Delegate business logic to `VenueService`.
     * - Return appropriate HTTP status codes (201, 200, 204, 404, 400).
     */

    @Operation(summary = "Create venue", description = "Creates a new venue")
    @ApiResponse(responseCode = "201", description = "Venue created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<VenueDTO> create(@Valid @RequestBody VenueDTO dto) {
        VenueDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/venues/" + created.getId())).body(created);
    }

    /**
     * Create a new venue. Returns 201 Created with Location header.
     */

    @Operation(summary = "List venues", description = "Get all venues")
    @ApiResponse(responseCode = "200", description = "List of venues retrieved")
    @GetMapping
    public ResponseEntity<List<VenueDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    /**
     * Return a list of venues (200 OK).
     */

    @Operation(summary = "Get venue by id", description = "Get a single venue by its id")
    @ApiResponse(responseCode = "200", description = "Venue found")
    @ApiResponse(responseCode = "404", description = "Venue not found")
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Get a single venue by id. Returns 200 OK or 404 Not Found.
     */

    @Operation(summary = "Update venue", description = "Update an existing venue")
    @ApiResponse(responseCode = "200", description = "Venue updated")
    @ApiResponse(responseCode = "404", description = "Venue not found")
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> update(@PathVariable Long id, @Valid @RequestBody VenueDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Update an existing venue. Returns 200 OK with the updated resource.
     */

    @Operation(summary = "Delete venue", description = "Delete a venue by its id")
    @ApiResponse(responseCode = "204", description = "Venue deleted")
    @ApiResponse(responseCode = "404", description = "Venue not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete a venue by id. Returns 204 No Content on success.
     */
}
