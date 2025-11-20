// ...existing code...
package com.tiquets.catalogo.config;

import com.tiquets.catalogo.application.port.in.EventCreateUseCase;
import com.tiquets.catalogo.application.port.out.EventRepositoryPort;
import com.tiquets.catalogo.application.service.EventService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {


    public EventCreateUseCase eventCreate(EventRepositoryPort eventRepositoryPort) {
        return new EventService(eventRepositoryPort);
    }

}