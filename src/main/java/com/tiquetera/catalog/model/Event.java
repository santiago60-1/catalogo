package com.tiquetera.catalog.model;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String name;
    private String description;
    private Long venueId;
    private LocalDateTime dateTime;

    public Event(Long id, String name, String description, Long venueId, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.venueId = venueId;
        this.dateTime = dateTime;
        validateInvariant();
    }

    private void validateInvariant() {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Event name must not be blank");
        if (venueId == null) throw new IllegalArgumentException("Venue id is required");
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}
