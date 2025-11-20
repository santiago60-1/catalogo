package com.tiquets.catalogo.application.port.in;

import com.tiquets.catalogo.domain.Events;

public interface EventFindById {

    Events findById(String id);

}
