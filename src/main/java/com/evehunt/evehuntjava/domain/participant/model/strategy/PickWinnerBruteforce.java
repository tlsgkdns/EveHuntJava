package com.evehunt.evehuntjava.domain.participant.model.strategy;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;

import java.util.List;
import java.util.Objects;

public class PickWinnerBruteforce implements PickWinnerStrategy{
    @Override
    public List<Participant> pick(List<Participant> participantList, List<Long> winnerList) {
        for(Long winner: winnerList)
            for(Participant participant : participantList)
            {
                if(Objects.equals(winner, participant.getId()))
                {
                    participant.setStatus(ParticipantStatus.WIN);
                    break;
                }
            }
        for(Participant participant : participantList)
            if(participant.getStatus() != ParticipantStatus.WIN)
                participant.setStatus(ParticipantStatus.LOSE);
        return participantList;
    }
}
