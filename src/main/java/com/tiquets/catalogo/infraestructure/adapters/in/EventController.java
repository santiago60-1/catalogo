package com.tiquets.catalogo.infraestructure.adapters.in;

import com.tiquets.catalogo.application.port.in.*;
import com.tiquets.catalogo.domain.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventCreateUseCase eventCreate;
    private final EventGetUseCases eventGet;
    private  final EventGetByName eventGetByName;
    private final EventUpdateUseCase eventUpdate;
    private final EventDeleteUseCase eventDelete;
    private final EventFindById eventFindById;

    @PostMapping
    public ResponseEntity<Events> create(@RequestBody Events events){
        return ResponseEntity.ok(eventCreate.createEvents(events));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Events> update(@PathVariable String id,
    @RequestBody Events events ){
        events.setId(id);
        return ResponseEntity.ok(eventUpdate.updateEvents(events));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        eventDelete.deleteEvents(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Events> getById(@PathVariable String id){
        return ResponseEntity.ok(eventFindById.findById(id));
    }

    @GetMapping("/{search}")
    public ResponseEntity<List<Events>> getByName(@RequestParam String name){
        return ResponseEntity.ok(eventGetByName.getEventsByName(name));
    }

    @GetMapping
    public ResponseEntity<Events> getAll(){
        return ResponseEntity.ok(eventGet.getEvents());
    }




}
