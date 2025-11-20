package com.tiquets.catalogo.application.port.in;

import com.tiquets.catalogo.domain.Events;
import org.springframework.context.annotation.Bean;

public interface EventGetUseCases {

    public Events getEvents();
}
