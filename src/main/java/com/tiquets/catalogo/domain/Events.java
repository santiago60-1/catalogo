package com.tiquets.catalogo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name = "events") // opcional, puedes cambiar el nombre de la tabla si lo deseas
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // genera automáticamente un UUID como clave primaria
    private String id;

    private String name;
    private String description;
    private String date;

    // Constructor vacío obligatorio para JPA
    public Events() {
    }

    // Constructor con parámetros
    public Events(String id, String name, String description, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    // getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}