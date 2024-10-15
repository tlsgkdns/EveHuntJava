package com.evehunt.evehuntjava.domain.participant.model.strategy;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;

import java.util.Comparator;
import java.util.List;

public class PickWinnerTwoPointer implements PickWinnerStrategy{

    @Override
    public List<Participant> pick(List<Participant> participantList, List<Long> winnerList) {
         participantList.sort(new Comparator<Participant>() {
            @Override
            public int compare(Participant o1, Participant o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
         List<Long> sortedId = participantList.stream().map(Participant::getId).toList();
         int winnerIdx = 0;
         for(int i = 0 ; i < sortedId.size(); i++)
         {
             if(winnerList.size() <= winnerIdx || winnerList.get(winnerIdx) != sortedId.get(i))
                 participantList.get(i).setStatus(ParticipantStatus.LOSE);
             else {
                 participantList.get(i).setStatus(ParticipantStatus.WIN);
                 winnerIdx++;
             }
         }
         return participantList;
    }
}
