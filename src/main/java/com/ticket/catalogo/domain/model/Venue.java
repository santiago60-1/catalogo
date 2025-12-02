package com.ticket.catalogo.domain.model;

import java.util.List;

public class Venue {

    private Long id;
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    private List<Evento> eventos;
    // getters, setters, constructores


    public Venue(Long id, String nombre, Integer capacidad, String ubicacion, List<Evento> eventos) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.eventos = eventos;
    }

    public Venue() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
