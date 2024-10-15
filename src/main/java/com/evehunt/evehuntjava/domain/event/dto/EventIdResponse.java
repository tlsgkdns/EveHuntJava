package com.evehunt.evehuntjava.domain.event.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class EventIdResponse {
    private final Long id;

    public final Long getId() {
        return this.id;
    }

    public EventIdResponse(Long id) {
        this.id = id;
    }
}
