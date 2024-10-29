package com.evehunt.evehuntjava.domain.participant.dto;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.participant.model.Participant;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class ParticipantRequest {

    private final String answer;

    @NotNull
    public final Participant to(@NotNull Event event, Member member) {
        return new Participant(event, member, this.answer);
    }

    @NotNull
    public final String getAnswer() {
        return this.answer;
    }

    public ParticipantRequest(@NotNull String answer) {
        this.answer = answer;
    }
}
