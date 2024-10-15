package com.evehunt.evehuntjava.domain.participant.dto;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class ParticipateResponse {
    private final Long id;

    private final Long eventId;

    private final Long memberId;
    @NotNull
    private final String answer;
    @NotNull
    private ParticipantStatus status;

    private final String email;

    public final Long getId() {
        return this.id;
    }


    public final Long getEventId() {
        return this.eventId;
    }


    public final Long getMemberId() {
        return this.memberId;
    }

    @NotNull
    public final String getAnswer() {
        return this.answer;
    }

    @NotNull
    public final ParticipantStatus getStatus() {
        return this.status;
    }

    public final void setStatus(@NotNull ParticipantStatus status) {
        this.status = status;
    }

    public final String getEmail() {
        return this.email;
    }

    public ParticipateResponse(Long id, Long eventId, Long memberId,
                               @NotNull String answer, @NotNull ParticipantStatus status, String email) {
        this.id = id;
        this.eventId = eventId;
        this.memberId = memberId;
        this.answer = answer;
        this.status = status;
        this.email = email;
    }

    public static ParticipateResponse from(@NotNull Participant participant) {
        return new ParticipateResponse(participant.getId(), participant.getEvent().getId(), participant.getParticipant().getId(),
                participant.getAnswer(), participant.getStatus(), participant.getParticipant().getEmail());
    }
}
