package com.tiquetera.catalog.mapper;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.model.Venue;

public class VenueMapper {

    public static Venue toDomain(VenueDTO dto) {
        if (dto == null) return null;
        return new Venue(
            dto.getId(),
            dto.getName(),
            dto.getAddress(),
            dto.getCapacity()
        );
    }

    public static VenueDTO toDto(Venue domain) {
        if (domain == null) return null;
        VenueDTO dto = new VenueDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setAddress(domain.getAddress());
        dto.setCapacity(domain.getCapacity());
        return dto;
    }
}
