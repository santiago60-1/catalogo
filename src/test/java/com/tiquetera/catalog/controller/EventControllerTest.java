package com.tiquetera.catalog.controller;

import com.tiquetera.catalog.dto.EventDTO;
import com.tiquetera.catalog.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.tiquetera.catalog.config.TestConfig;

@WebMvcTest(EventController.class)
@Import(TestConfig.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    private EventDTO sampleEvent;

    @BeforeEach
    void setUp() {
        sampleEvent = new EventDTO();
        sampleEvent.setId(1L);
        sampleEvent.setName("Test Event");
        sampleEvent.setDescription("Test Description");
        sampleEvent.setVenueId(1L);
        sampleEvent.setDate("2025-12-31T23:59:59");
    }

    @Test
    void createEvent_Success() throws Exception {
        when(eventService.create(any(EventDTO.class))).thenReturn(sampleEvent);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleEvent)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/events/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }

    @Test
    void createEvent_InvalidData() throws Exception {
        EventDTO invalidEvent = new EventDTO();
        // Name is required
        invalidEvent.setVenueId(1L);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEvent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEvent_Success() throws Exception {
        when(eventService.getById(1L)).thenReturn(sampleEvent);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Event"));
    }

    @Test
    void getEvent_NotFound() throws Exception {
        when(eventService.getById(999L)).thenThrow(new NoSuchElementException("Event not found"));

        mockMvc.perform(get("/events/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void listEvents_Success() throws Exception {
        when(eventService.list()).thenReturn(Arrays.asList(sampleEvent));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Event"));
    }

    @Test
    void updateEvent_Success() throws Exception {
        EventDTO updatedEvent = sampleEvent;
        updatedEvent.setName("Updated Event");
        
        when(eventService.update(eq(1L), any(EventDTO.class))).thenReturn(updatedEvent);

        mockMvc.perform(put("/events/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Event"));
    }

    @Test
    void deleteEvent_Success() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEvent_NotFound() throws Exception {
        doThrow(new NoSuchElementException("Event not found"))
            .when(eventService).delete(999L);

        mockMvc.perform(delete("/events/999"))
                .andExpect(status().isNotFound());
    }
}