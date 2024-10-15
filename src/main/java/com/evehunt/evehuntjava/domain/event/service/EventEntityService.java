package com.evehunt.evehuntjava.domain.event.service;

import com.evehunt.evehuntjava.domain.event.dto.*;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface EventEntityService {
    @NotNull
    EventResponse editEvent(Long eventId, @NotNull EventEditRequest var2);

    @NotNull
    EventResponse hostEvent(@NotNull EventHostRequest var1, @NotNull String var2);

    @NotNull
    EventResponse getEvent(Long eventId);

    @NotNull
    PageResponse<EventCardResponse> getEvents(@NotNull PageRequest var1);

    @NotNull
    List<EventCardResponse> getPopularEvent();

    @NotNull
    List<EventIdResponse> setExpiredEventsClose();

    @NotNull
    EventResponse closeEvent(Long eventId);
    @NotNull
    EventResponse announceEvent(Long eventId);
}
