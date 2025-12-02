package com.ticket.catalogo.adapters.out.persistence.jpa.adapter;

import com.ticket.catalogo.adapters.out.persistence.jpa.entity.JpaVenueEntity;
import com.ticket.catalogo.adapters.out.persistence.jpa.mapper.JpaVenueMapper;
import com.ticket.catalogo.adapters.out.persistence.jpa.repository.SpringDataVenueRepository;
import com.ticket.catalogo.application.port.out.VenueRepositoryPort;
import com.ticket.catalogo.domain.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaVenueRepositoryAdapter implements VenueRepositoryPort {

    private final SpringDataVenueRepository repo;

    public JpaVenueRepositoryAdapter(SpringDataVenueRepository repo) {
        this.repo = repo;
    }

    @Override
    public Venue save(Venue venue) {
        JpaVenueEntity entity = JpaVenueMapper.toEntity(venue);
        JpaVenueEntity saved = repo.save(entity);
        return JpaVenueMapper.toDomain(saved);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return repo.findById(id).map(JpaVenueMapper::toDomain);
    }

    @Override
    public List<Venue> findAll() {
        return repo.findAll().stream()
                .map(JpaVenueMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Venue> findByVenueId(Long venueId) {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}

