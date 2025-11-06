package com.tiquetera.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EventDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    /**
     * ISO_LOCAL_DATE_TIME string, e.g. "2025-11-04T18:00:00"
     */
    private String date;

    @Size(max = 100)
    private String category;

    private Long venueId;

    private String venueName;

    public EventDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
}
