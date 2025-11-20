package com.tiquets.catalogo.application.port.in;

import com.tiquets.catalogo.domain.Events;

public interface EventCreateUseCase {

    public Events createEvents(Events Events);
}
