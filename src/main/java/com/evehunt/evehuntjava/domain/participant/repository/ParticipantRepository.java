package com.evehunt.evehuntjava.domain.participant.repository;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, QueryDslParticipantRepository
{

}
