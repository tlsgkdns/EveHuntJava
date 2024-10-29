package com.evehunt.evehuntjava.domain.event.service;

import com.evehunt.evehuntjava.domain.event.dto.*;
import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantResponse;
import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface EventService {
    @NotNull
    EventResponse editEvent(Long eventId, @NotNull EventEditRequest request);

    @NotNull
    EventResponse hostEvent(@NotNull EventHostRequest request, @NotNull String email);

    @NotNull
    EventResponse getEvent(Long eventId);

    @NotNull
    PageResponse<EventCardResponse> getEvents(@NotNull PageRequest request);

    @NotNull
    List<EventIdResponse> setExpiredEventsClose();

    @NotNull
    ParticipantResponse participateEvent(Long eventId, @NotNull String email, @NotNull ParticipantRequest request);

    void resignEventParticipate(Long eventId, @NotNull String email);

    @NotNull
    List<EventCardResponse> getPopularEvent();

    @NotNull
    List<ParticipantResponse> setEventResult(Long eventId, @NotNull EventWinnerRequest request);

    @NotNull
    List<ParticipantResponse> getParticipants(Long eventId);

    @NotNull
    ParticipantResponse getParticipant(Long eventId, @NotNull String email);

    @NotNull
    List<TagResponse> getTags(Long eventId);

    @NotNull
    void closeEvent(Long eventId);

    @NotNull
    TagResponse addTag(Long eventId, @NotNull TagAddRequest request);

    void deleteTag(Long eventId, Long tagId);
}
