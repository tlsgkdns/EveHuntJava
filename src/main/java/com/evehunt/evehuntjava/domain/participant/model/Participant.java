package com.evehunt.evehuntjava.domain.participant.model;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity

public final class Participant extends BaseTimeEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private final Long id = null;
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private final Event event;
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @ManyToOne(fetch = FetchType.LAZY)
    private final Member participant;
    @NotNull
    private String answer;

    public Participant() {
        event = null;
        participant = null;
    }

    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final ParticipantStatus getStatus() {
        return this.status;
    }

    public final void setStatus(@NotNull ParticipantStatus status) {
        this.status = status;
    }

    @NotNull
    public final Event getEvent() {
        return this.event;
    }

    public final Member getParticipant() {
        return this.participant;
    }

    @NotNull
    public final String getAnswer() {
        return this.answer;
    }

    public final void setAnswer(@NotNull String var1) {
        this.answer = var1;
    }

    public Participant(@NotNull Event event, Member participant, @NotNull String answer) {
        this.event = event;
        this.participant = participant;
        this.answer = answer;
        this.status = ParticipantStatus.PARTICIPATING;
    }
}
