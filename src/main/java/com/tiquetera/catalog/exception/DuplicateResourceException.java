package com.tiquetera.catalog.exception;

/**
 * Excepción runtime que representa un conflicto por recurso duplicado (HTTP 409).
 * Lanzarla desde la capa de servicio cuando se detecta una violación de unicidad,
 * por ejemplo: nombre de evento ya existente.
 */
public class DuplicateResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DuplicateResourceException() {
        super();
    }

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateResourceException(Throwable cause) {
        super(cause);
    }

    /**
     * Conveniencia para construir un mensaje estandarizado por entidad y campo.
     */
    public static DuplicateResourceException forField(String entityName, String fieldName, Object value) {
        return new DuplicateResourceException(
            String.format("%s duplicado: %s = %s", entityName, fieldName, String.valueOf(value))
        );
    }
}
