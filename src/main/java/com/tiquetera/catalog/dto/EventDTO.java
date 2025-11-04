package com.tiquetera.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Event details")
public class EventDTO {
    /**
     * Data Transfer Object for Event.
     *
     * This DTO is used as the request and response payload for Event endpoints.
     * Validation annotations ensure required fields are present on create/update.
     */
    @Schema(description = "Event ID", example = "1")
    private Long id;

    @NotBlank(message = "Event name must not be blank")
    @Schema(description = "Event name", example = "Summer Concert 2026", required = true)
    private String name;

    @Schema(description = "Event description", example = "Annual summer concert series")
    private String description;

    @NotNull(message = "Venue id is required")
    @Schema(description = "Venue ID where event takes place", example = "1", required = true)
    private Long venueId;

    @Schema(description = "Event date (ISO-8601)", example = "2026-03-10T20:00:00")
    private String date;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}
