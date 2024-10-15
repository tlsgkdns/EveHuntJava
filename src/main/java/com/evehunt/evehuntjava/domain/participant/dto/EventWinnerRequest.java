package com.evehunt.evehuntjava.domain.participant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor(force = true)
public class EventWinnerRequest {
    @NotNull
    private final List<Long> eventWinners;
    @NotNull
    private final List<String> winMessages;

    @NotNull
    public final List<Long> getEventWinners() {
        return this.eventWinners;
    }

    @NotNull
    public final List<String> getWinMessages() {
        return this.winMessages;
    }

    public EventWinnerRequest(@NotNull List<Long> eventWinners, @NotNull List<String> winMessages) {
        this.eventWinners = eventWinners;
        this.winMessages = winMessages;
    }

}
