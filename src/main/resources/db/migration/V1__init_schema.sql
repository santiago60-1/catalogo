-- V1__init_schema.sql
-- Crea tablas `venue` y `evento` y la clave foránea
CREATE TABLE IF NOT EXISTS venue (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255),
  capacidad INT,
  ubicacion VARCHAR(255),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS evento (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(255),
  fechaInicio DATETIME,
  fechaFin DATETIME,
  estado VARCHAR(50),
  venue_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_evento_venue FOREIGN KEY (venue_id) REFERENCES venue(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Nota: los nombres de columnas usan snake_case; los mapeos JPA usan camelCase.
