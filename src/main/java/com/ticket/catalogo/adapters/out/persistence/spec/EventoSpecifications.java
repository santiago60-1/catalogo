package com.ticket.catalogo.adapters.out.persistence.spec;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaEventoEntity;
import com.ticket.catalogo.application.port.out.EventoSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventoSpecifications {

    public static Specification<JpaEventoEntity> conEstado(String estado) {
        return (root, query, cb) -> cb.equal(root.get("estado"), estado);
    }

    public static Specification<JpaEventoEntity> conFechaInicioDespues(LocalDateTime fecha) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaInicio"), fecha);
    }

    public static Specification<JpaEventoEntity> conFechaFinAntes(LocalDateTime fecha) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaFin"), fecha);
    }

    public static Specification<JpaEventoEntity> enVenue(Long venueId) {
        return (root, query, cb) -> cb.equal(root.get("venue").get("id"), venueId);
    }

    public static Specification<JpaEventoEntity> conCategoria(String categoria) {
        return (root, query, cb) -> cb.equal(root.get("categoria"), categoria);
    }

    public static Specification<JpaEventoEntity> nombreLike(String nombreLike) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + nombreLike.toLowerCase() + "%");
    }

    /**
     * Construye una Specification combinada a partir de EventoSearchCriteria.
     */
    public static Specification<JpaEventoEntity> fromCriteria(EventoSearchCriteria c) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (c == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            if (c.getEstado() != null && !c.getEstado().isBlank()) {
                predicates.add(cb.equal(root.get("estado"), c.getEstado()));
            }
            if (c.getFechaInicioDesde() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fechaInicio"), c.getFechaInicioDesde()));
            }
            if (c.getFechaFinHasta() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fechaFin"), c.getFechaFinHasta()));
            }
            if (c.getVenueId() != null) {
                predicates.add(cb.equal(root.get("venue").get("id"), c.getVenueId()));
            }
            if (c.getCategoria() != null && !c.getCategoria().isBlank()) {
                predicates.add(cb.equal(root.get("categoria"), c.getCategoria()));
            }
            if (c.getNombreLike() != null && !c.getNombreLike().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + c.getNombreLike().toLowerCase() + "%"));
            }

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}