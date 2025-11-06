package com.tiquetera.catalog.repository.spec;

import com.tiquetera.catalog.model.EventEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import java.time.LocalDateTime;

public final class EventSpecifications {

    private EventSpecifications() { }

    /**
     * Filtra eventos cuyo venue.city contiene el valor (case-insensitive).
     * Retorna null si city es null o vacío para que pueda ser ignorada en la composición.
     */
    public static Specification<EventEntity> cityContains(String city) {
        if (city == null || city.isBlank()) return null;
        String pattern = "%" + city.trim().toLowerCase() + "%";
        return (root, query, cb) -> {
            Join<Object, Object> venue = root.join("venue");
            return cb.like(cb.lower(venue.get("city")), pattern);
        };
    }

    /**
     * Filtra por categoría (case-insensitive, contains).
     * Retorna null si category es null o vacío.
     */
    public static Specification<EventEntity> categoryContains(String category) {
        if (category == null || category.isBlank()) return null;
        String pattern = "%" + category.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.like(cb.lower(root.get("category")), pattern);
    }

    /**
     * Filtra eventos cuyo startDate es mayor o igual a 'from'.
     * Retorna null si from es null.
     */
    public static Specification<EventEntity> startDateAfterOrEqual(LocalDateTime from) {
        if (from == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startDate"), from);
    }

    /**
     * Conveniencia: compone las tres especificaciones (ignora las que son null).
     * Útil para evitar repetir la composición en el servicio.
     */
    public static Specification<EventEntity> compose(String city, String category, LocalDateTime from) {
        Specification<EventEntity> spec = Specification.where(cityContains(city))
                .and(categoryContains(category))
                .and(startDateAfterOrEqual(from));
        return spec;
    }
}
