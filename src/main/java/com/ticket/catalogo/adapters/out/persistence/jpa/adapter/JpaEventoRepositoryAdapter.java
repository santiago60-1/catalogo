package com.ticket.catalogo.adapters.out.persistence.jpa.adapter;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaEventoEntity;
import com.ticket.catalogo.adapters.out.persistence.jpa.mapper.JpaVenueMapper;
import com.ticket.catalogo.adapters.out.persistence.jpa.repository.SpringDataEventoRepository;

import com.ticket.catalogo.application.port.out.EventoRespositoryPort;
import com.ticket.catalogo.application.port.out.EventoSearchCriteria;
import com.ticket.catalogo.domain.model.Evento;
import com.ticket.catalogo.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaEventoRepositoryAdapter implements EventoRespositoryPort {

    private final SpringDataEventoRepository repo;
    private final JpaVenueMapper venueMapper = new JpaVenueMapper();

    public JpaEventoRepositoryAdapter(SpringDataEventoRepository repo) {
        this.repo = repo;
    }

    @Override
    public Evento save(Evento evento) {
        JpaEventoEntity entity = toEntity(evento);
        JpaEventoEntity saved = repo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<Evento> findAll() {
        return repo.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Evento> findByVenueId(Long venueId) {
        return repo.findByVenueId(venueId).stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<Evento> search(EventoSearchCriteria criteria, Pageable pageable) {
        Specification<JpaEventoEntity> spec =
                com.ticket.catalogo.adapters.out.persistence.spec.EventoSpecifications.fromCriteria(criteria);

        Page<JpaEventoEntity> pageEntities = repo.findAll(spec, pageable);

        return pageEntities.map(this::toDomain);
    }


    /* Mapeos simples */
    private Evento toDomain(JpaEventoEntity e) {
        Venue v = e.getVenue() == null ? null : venueMapper.toDomain(e.getVenue());
        Evento d = new Evento();
        d.setId(e.getId());
        d.setNombre(e.getNombre());
        d.setFechaInicio(e.getFechaInicio());
        d.setFechaFin(e.getFechaFin());
        d.setEstado(e.getEstado());
        d.setVenue(v);
        return d;
    }

    private JpaEventoEntity toEntity(Evento d) {
        JpaEventoEntity e = new JpaEventoEntity();
        e.setId(d.getId());
        e.setNombre(d.getNombre());
        e.setFechaInicio(d.getFechaInicio());
        e.setFechaFin(d.getFechaFin());
        e.setEstado(d.getEstado());
        e.setVenue(d.getVenue() == null ? null : venueMapper.toEntity(d.getVenue()));
        return e;
    }
}