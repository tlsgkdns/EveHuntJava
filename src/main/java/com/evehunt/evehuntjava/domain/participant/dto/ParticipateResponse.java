package com.evehunt.evehuntjava.domain.participant.dto;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public final void setStatus(@NotNull ParticipantStatus status) {
        this.status = status;
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
