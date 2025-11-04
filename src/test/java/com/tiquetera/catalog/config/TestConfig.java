package com.tiquetera.catalog.config;

import com.tiquetera.catalog.repository.EventRepository;
import com.tiquetera.catalog.repository.VenueRepository;
import com.tiquetera.catalog.service.EventService;
import com.tiquetera.catalog.service.VenueService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    
    @Bean
    public VenueRepository venueRepository() {
        return new VenueRepository();
    }

    @Bean
    public EventRepository eventRepository() {
        return new EventRepository();
    }

    @Bean
    public VenueService venueService(VenueRepository venueRepository) {
        return new VenueService(venueRepository);
    }

    @Bean
    public EventService eventService(EventRepository eventRepository, VenueRepository venueRepository) {
        return new EventService(eventRepository, venueRepository);
    }
}