package com.tiquetera.catalog.mapper;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.model.Event;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;

public class EventMapper {
    public static Event toDomain(EventDTO dto) {
        if (dto == null) return null;
        LocalDateTime dt = dto.getDate() == null ? null : LocalDateTime.parse(dto.getDate());
        return new Event(dto.getId(), dto.getName(), dto.getDescription(), dto.getVenueId(), dt);
    }

    @GetMapping

    public static EventDTO toDto(Event e) {
        if (e == null) return null;
        EventDTO dto = new EventDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDescription(e.getDescription());
        dto.setVenueId(e.getVenueId());
        dto.setDate(e.getDateTime() == null ? null : e.getDateTime().toString());
        return dto;
    }
}
