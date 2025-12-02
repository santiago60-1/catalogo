package com.ticket.catalogo.application.DTO;

import com.ticket.catalogo.domain.model.Evento;
import com.ticket.catalogo.domain.model.Venue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Schema(description = "DTO para Evento usado en la API")
public class EventoDto {

    private Long id;

    @Schema(description = "Nombre del evento", example = "Concierto Rock")
    private String nombre;

    @Schema(description = "Fecha y hora de inicio (ISO-8601)", example = "2025-12-10T20:00:00")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha y hora de fin (ISO-8601)", example = "2025-12-10T23:00:00")
    private LocalDateTime fechaFin;

    @Schema(description = "Estado del evento", example = "ACTIVO")
    private String estado;

    @Schema(description = "Id del venue asociado", example = "1")
    private Long venueId;

    @Schema(description = "Nombre del venue asociado", example = "Auditorio Nacional")
    private String venueNombre;

    public static EventoDto fromDomain(Evento e) {
        if (e == null) return null;
        EventoDto d = new EventoDto();
        d.setId(e.getId());
        d.setNombre(e.getNombre());
        d.setFechaInicio(e.getFechaInicio());
        d.setFechaFin(e.getFechaFin());
        d.setEstado(e.getEstado());
        if (e.getVenue() != null) {
            d.setVenueId(e.getVenue().getId());
            d.setVenueNombre(e.getVenue().getNombre());
        }
        return d;
    }

    public Evento toDomain() {
        Evento e = new Evento();
        e.setId(this.id);
        e.setNombre(this.nombre);
        e.setFechaInicio(this.fechaInicio);
        e.setFechaFin(this.fechaFin);
        e.setEstado(this.estado);
        if (this.venueId != null) {
            Venue v = new Venue();
            v.setId(this.venueId);
            e.setVenue(v);
        }
        return e;
    }
}



