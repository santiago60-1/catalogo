package com.tiquetera.catalog.repository;

import com.tiquetera.catalog.model.Event;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepository {
    private final Map<Long, Event> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Event save(Event e) {
        if (e.getId() == null) e.setId(idGen.getAndIncrement());
        store.put(e.getId(), e);
        return e;
    }

    public Optional<Event> findById(Long id) { return Optional.ofNullable(store.get(id)); }
    public List<Event> findAll() { return new ArrayList<>(store.values()); }
    public void deleteById(Long id) { store.remove(id); }
    public void clear() { store.clear(); idGen.set(1); }
}
