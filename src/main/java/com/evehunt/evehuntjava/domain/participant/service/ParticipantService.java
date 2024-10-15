package com.evehunt.evehuntjava.domain.participant.service;

import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface ParticipantService {
    @NotNull
    ParticipateResponse participateEvent(Long id, @NotNull String email, @NotNull ParticipateRequest request);

    void resignEventParticipate( Long id, @NotNull String email);

    @NotNull
    List<ParticipateResponse> setEventResult(Long id, @NotNull EventWinnerRequest request);

    @NotNull
    PageResponse<ParticipateResponse> getParticipateHistoryByMember(@NotNull String email, @NotNull PageRequest request);

    @NotNull
    List<ParticipateResponse> getParticipantsByEvent(Long id);

    @NotNull
    ParticipateResponse getParticipant( Long id, @NotNull String email);

    @NotNull
    List<ParticipateResponse> setParticipantsStatusWait( Long id);

    int getParticipantCount( Long id);
}
