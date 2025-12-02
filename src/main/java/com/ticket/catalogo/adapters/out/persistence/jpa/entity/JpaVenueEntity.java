package com.ticket.catalogo.adapters.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// infrastructure/persistence/jpa/JpaVenueEntity.java
@Entity
@Table(name="venue")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JpaVenueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer capacidad;
    private String ubicacion;
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<JpaEventoEntity> eventos;
}

