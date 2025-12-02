package com.ticket.catalogo.adapters.out.persistence.jpa.mapper;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaVenueEntity;
import com.ticket.catalogo.domain.model.Venue;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Mapper simple entre JpaVenueEntity <-> domain.model.Venue.
 * - Evita mapear la colección de eventos para no provocar recursión accidental.
 * - Si necesitas mapear eventos, crea/inyecta un EventoMapper y úsalo aquí.
 */
public final class JpaVenueMapper {

    public JpaVenueMapper() {
        // utilitario
    }

    public static Venue toDomain(JpaVenueEntity entity) {
        if (entity == null) return null;

        Venue domain = new Venue();
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setCapacidad(entity.getCapacidad());
        domain.setUbicacion(entity.getUbicacion());

        // NOTA: no mapeamos eventos aquí para evitar recursión y N+1.
        // Si necesitas mapear eventos, inyecta/usa un EventoMapper y descomenta:
        // domain.setEventos(entity.getEventos() == null ? null :
        //     entity.getEventos().stream().map(EventoMapper::toDomain).collect(Collectors.toList()));

        return domain;
    }

    public static JpaVenueEntity toEntity(Venue domain) {
        if (domain == null) return null;

        JpaVenueEntity entity = new JpaVenueEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setCapacidad(domain.getCapacidad());
        entity.setUbicacion(domain.getUbicacion());

        // NOTA: no mapeamos eventos aquí. Si necesitas mapear eventos, usa EventoMapper:
        // entity.setEventos(domain.getEventos() == null ? null :

        return entity;
    }

    public static List<Venue> toDomainList(List<JpaVenueEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .filter(Objects::nonNull)
                .map(JpaVenueMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static List<JpaVenueEntity> toEntityList(List<Venue> domains) {
        if (domains == null) return null;
        return domains.stream()
                .filter(Objects::nonNull)
                .map(JpaVenueMapper::toEntity)
                .collect(Collectors.toList());
    }
}
