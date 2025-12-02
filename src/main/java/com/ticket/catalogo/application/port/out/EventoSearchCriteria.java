package com.ticket.catalogo.application.port.out;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Contenedor de criterios para búsquedas de Evento.
 * La infraestructura debe convertirlo a Specification<JpaEventoEntity>.
 */
public class EventoSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String estado;
    private final LocalDateTime fechaInicioDesde;
    private final LocalDateTime fechaFinHasta;
    private final Long venueId;
    private final String categoria;
    private final String nombreLike;

    private EventoSearchCriteria(Builder b) {
        this.estado = b.estado;
        this.fechaInicioDesde = b.fechaInicioDesde;
        this.fechaFinHasta = b.fechaFinHasta;
        this.venueId = b.venueId;
        this.categoria = b.categoria;
        this.nombreLike = b.nombreLike;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaInicioDesde() {
        return fechaInicioDesde;
    }

    public LocalDateTime getFechaFinHasta() {
        return fechaFinHasta;
    }

    public Long getVenueId() {
        return venueId;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getNombreLike() {
        return nombreLike;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String estado;
        private LocalDateTime fechaInicioDesde;
        private LocalDateTime fechaFinHasta;
        private Long venueId;
        private String categoria;
        private String nombreLike;

        private Builder() {}

        public Builder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public Builder fechaInicioDesde(LocalDateTime fechaInicioDesde) {
            this.fechaInicioDesde = fechaInicioDesde;
            return this;
        }

        public Builder fechaFinHasta(LocalDateTime fechaFinHasta) {
            this.fechaFinHasta = fechaFinHasta;
            return this;
        }

        public Builder venueId(Long venueId) {
            this.venueId = venueId;
            return this;
        }

        public Builder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder nombreLike(String nombreLike) {
            this.nombreLike = nombreLike;
            return this;
        }

        public EventoSearchCriteria build() {
            return new EventoSearchCriteria(this);
        }
    }

    @Override
    public String toString() {
        return "EventoSearchCriteria{" +
                "estado='" + estado + '\'' +
                ", fechaInicioDesde=" + fechaInicioDesde +
                ", fechaFinHasta=" + fechaFinHasta +
                ", venueId=" + venueId +
                ", categoria='" + categoria + '\'' +
                ", nombreLike='" + nombreLike + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventoSearchCriteria)) return false;
        EventoSearchCriteria that = (EventoSearchCriteria) o;
        return Objects.equals(estado, that.estado) &&
                Objects.equals(fechaInicioDesde, that.fechaInicioDesde) &&
                Objects.equals(fechaFinHasta, that.fechaFinHasta) &&
                Objects.equals(venueId, that.venueId) &&
                Objects.equals(categoria, that.categoria) &&
                Objects.equals(nombreLike, that.nombreLike);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estado, fechaInicioDesde, fechaFinHasta, venueId, categoria, nombreLike);
    }
}
