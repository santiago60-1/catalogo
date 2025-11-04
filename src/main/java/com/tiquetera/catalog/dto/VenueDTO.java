package com.tiquetera.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Venue details")
public class VenueDTO {
    /**
     * Data Transfer Object for Venue.
     *
     * Used for requests and responses in the Venues REST API. Validation
     * annotations guard input values (for example, capacity cannot be negative).
     */
    @Schema(description = "Venue ID", example = "1")
    private Long id;

    @NotBlank(message = "Venue name must not be blank")
    @Schema(description = "Venue name", example = "Estadio Central", required = true)
    private String name;

    @Schema(description = "Venue address", example = "Av 1 #2-3")
    private String address;

    @Min(value = 0, message = "Capacity cannot be negative")
    @Schema(description = "Venue capacity", example = "20000", minimum = "0")
    private Integer capacity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
