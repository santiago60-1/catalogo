package com.tiquetera.catalog.exception;

/**
 * Excepción runtime que representa recursos no encontrados (HTTP 404).
 * Se lanza desde la capa de servicio cuando una entidad esperada no existe.
 */
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Crea una NotFoundException con el mensaje estándar para una entidad por id.
     */
    public static NotFoundException forId(String entityName, Object id) {
        return new NotFoundException(entityName + " not found for id: " + id);
    }
}
