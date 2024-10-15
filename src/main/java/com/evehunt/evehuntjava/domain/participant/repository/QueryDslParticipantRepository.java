package com.evehunt.evehuntjava.domain.participant.repository;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDslParticipantRepository {
    Participant getParticipant(Long id, @NotNull String email);

    @NotNull
    List<Participant> getParticipantsByEvent(Long id);

    @NotNull
    Page<Participant> getParticipantsByMember(@NotNull Pageable request, @NotNull String email);
}
