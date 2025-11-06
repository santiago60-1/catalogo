package com.tiquetera.catalog.mapper;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.model.EventEntity;
import com.tiquetera.catalog.model.VenueEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public final class EventMapper {

    private EventMapper() {}

    public static EventEntity toDomain(EventDTO dto) {
        if (dto == null) return null;
        LocalDateTime dt = parseIsoDateTime(dto.getDate());
        return EventEntity.of(
                dto.getId(),
                dto.getName() == null ? null : dto.getName().trim(),
                dto.getDescription(),
                null, // venue assigned by service
                dt, // event date
                LocalDateTime.now(), // createdAt
                null, // updatedAt
                dto.getCategory()
        );
    }

    public static EventDTO toDto(EventEntity e) {
        if (e == null) return null;
        EventDTO dto = new EventDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setDate(e.getDate() == null ? null : e.getDate().toString());
        dto.setCategory(e.getCategory());
        if (e.getVenue() != null) {
            VenueEntity v = e.getVenue();
            dto.setVenueId(v.getId());
            dto.setVenueName(v.getName());
        }
        return dto;
    }

    private static LocalDateTime parseIsoDateTime(String iso) {
        if (iso == null || iso.isBlank()) return null;
        try { return LocalDateTime.parse(iso); } catch (DateTimeParseException ex) { return null; }
    }
}
