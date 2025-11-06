package com.tiquetera.catalog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import java.time.LocalDateTime;

@Entity
@Table(name = "events",
       uniqueConstraints = @UniqueConstraint(name = "uk_event_name", columnNames = "name")
)
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 200, message = "Name must be at most 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    @Column(name = "description", length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venue_id", nullable = false, foreignKey = @ForeignKey(name = "fk_event_venue"))
    private VenueEntity venue;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Size(max = 100, message = "Category must be at most 100 characters")
    @Column(name = "category", length = 100)
    private String category;

    protected EventEntity() {
        // required by JPA
    }

    public EventEntity(Long id, String name, String description, VenueEntity venue, LocalDateTime date, LocalDateTime createdAt, LocalDateTime updatedAt, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }

    public static EventEntity of(Long id, String name, String description, VenueEntity venue, LocalDateTime date, LocalDateTime createdAt, LocalDateTime updatedAt, String category) {
        return new EventEntity(id, name, description, venue, date, createdAt, updatedAt, category);
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name == null ? null : name.trim(); }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public VenueEntity getVenue() { return venue; }
    public void setVenue(VenueEntity venue) { this.venue = venue; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventEntity)) return false;
        EventEntity that = (EventEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
