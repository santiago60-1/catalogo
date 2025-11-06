package com.tiquetera.catalog.mapper;

import com.tiquetera.catalog.dto.VenueDTO;
import com.tiquetera.catalog.model.VenueEntity;

public final class VenueMapper {

    private VenueMapper() {}

    public static VenueEntity toDomain(VenueDTO dto) {
        if (dto == null) return null;
        return VenueEntity.of(dto.getId(),
                dto.getName() == null ? null : dto.getName().trim(),
                dto.getAddress(),
                dto.getCity(),
                dto.getCapacity());
    }

    public static VenueDTO toDto(VenueEntity e) {
        if (e == null) return null;
        VenueDTO dto = new VenueDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setAddress(e.getAddress());
        dto.setCity(e.getCity());
        dto.setCapacity(e.getCapacity());
        return dto;
    }
}
