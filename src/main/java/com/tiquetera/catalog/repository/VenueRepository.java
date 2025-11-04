package com.tiquetera.catalog.repository;

import com.tiquetera.catalog.model.Venue;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VenueRepository {
    private final Map<Long, Venue> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Venue save(Venue v) {
        if (v.getId() == null) v.setId(idGen.getAndIncrement());
        store.put(v.getId(), v);
        return v;
    }

    public Optional<Venue> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public List<Venue> findAll() { return new ArrayList<>(store.values()); }
    public void deleteById(Long id) { store.remove(id); }
    public void clear() { store.clear(); idGen.set(1); }
}
