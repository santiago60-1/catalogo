package com.ticket.catalogo.application.DTO;

import com.ticket.catalogo.domain.model.Venue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Schema(description = "DTO para Venue usado en la API")
public class VenueDto {

    private Long id;

    @Schema(description = "Nombre del venue", example = "Auditorio Nacional")
    private String nombre;

    @Schema(description = "Capacidad del venue", example = "5000")
    private Integer capacidad;

    @Schema(description = "Ubicación física", example = "Ciudad X, Calle 123")
    private String ubicacion;

    // Conversión desde domain
    public static VenueDto fromDomain(Venue v) {
        if (v == null) return null;
        VenueDto d = new VenueDto();
        d.setId(v.getId());
        d.setNombre(v.getNombre());
        d.setCapacidad(v.getCapacidad());
        d.setUbicacion(v.getUbicacion());
        return d;
    }

    // Conversión a domain
    public Venue toDomain() {
        Venue v = new Venue();
        v.setId(this.id);
        v.setNombre(this.nombre);
        v.setCapacidad(this.capacidad);
        v.setUbicacion(this.ubicacion);
        return v;
    }

    public static List<VenueDto> fromDomainList(List<Venue> list) {
        if (list == null) return Collections.emptyList();
        return list.stream().map(VenueDto::fromDomain).collect(Collectors.toList());
    }
}
