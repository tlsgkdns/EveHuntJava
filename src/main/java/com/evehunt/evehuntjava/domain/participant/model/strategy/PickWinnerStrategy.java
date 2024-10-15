package com.evehunt.evehuntjava.domain.participant.model.strategy;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface PickWinnerStrategy {
    @NotNull
    List<Participant> pick(@NotNull List<Participant> participantList, @NotNull List<Long> winnerList);
}