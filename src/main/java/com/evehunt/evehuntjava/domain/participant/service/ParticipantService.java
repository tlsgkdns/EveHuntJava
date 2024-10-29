package com.evehunt.evehuntjava.domain.participant.service;

import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ParticipantService {
    @NotNull
    ParticipantResponse participateEvent(Long id, @NotNull String email, @NotNull ParticipantRequest request);

    void resignEventParticipate( Long id, @NotNull String email);

    @NotNull
    List<ParticipantResponse> setEventResult(Long id, @NotNull EventWinnerRequest request);

    @NotNull
    PageResponse<ParticipantResponse> getParticipateHistoryByMember(@NotNull String email, @NotNull PageRequest request);

    @NotNull
    List<ParticipantResponse> getParticipantsByEvent(Long id);

    @NotNull
    ParticipantResponse getParticipant(Long id, @NotNull String email);

    @NotNull
    List<ParticipantResponse> setParticipantsStatusWait(Long id);

    int getParticipantCount( Long id);
}
