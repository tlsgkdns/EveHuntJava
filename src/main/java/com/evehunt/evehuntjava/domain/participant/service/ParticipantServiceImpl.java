package com.evehunt.evehuntjava.domain.participant.service;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.event.repository.EventRepository;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.member.repository.MemberRepository;
import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateResponse;
import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;
import com.evehunt.evehuntjava.domain.participant.model.strategy.PickWinnerStrategy;
import com.evehunt.evehuntjava.domain.participant.model.strategy.PickWinnerTwoPointer;
import com.evehunt.evehuntjava.domain.participant.repository.ParticipantRepository;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.AlreadyExistModelException;
import com.evehunt.evehuntjava.global.exception.exception.InvalidModelException;
import com.evehunt.evehuntjava.global.exception.exception.ModelNotFoundException;
import com.evehunt.evehuntjava.global.infra.aop.annotation.RedisLock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService{
    PickWinnerStrategy pickWinnerStrategy = new PickWinnerTwoPointer();
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;
    private final ParticipantRepository participantRepository;
    private Event getExistEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ModelNotFoundException("Event", String.valueOf(eventId)));
    }
    private Participant getExistParticipant(Long eventId, String email)
    {
        Participant participant = participantRepository.getParticipant(eventId, email);
        if(participant == null) throw new ModelNotFoundException("Participant", email);
        return participant;
    }
    @RedisLock
    @Override
    public ParticipateResponse participateEvent(Long id, String email, ParticipateRequest request) {
        if(participantRepository.getParticipant(id, email) != null) throw new AlreadyExistModelException(email);
        Event event = getExistEvent(id);
        Member member = memberRepository.findMemberByEmail(email);
        if(event.getEventStatus() != EventStatus.PROCEED) throw new InvalidModelException("Event");
        if(participantRepository.getParticipantsByEvent(id).size() + 1 > event.getCapacity())
            throw new InvalidModelException("Event");
        Participant participant = request.to(event, member);
        return ParticipateResponse.from(participant);
    }

    @Override
    public void resignEventParticipate(Long id, String email) {
        participantRepository.delete(getExistParticipant(id, email));
    }

    @Override
    public List<ParticipateResponse> setEventResult(Long id, EventWinnerRequest request) {
        List<Participant> participantList = participantRepository.getParticipantsByEvent(id);
        List<Participant> winnerList = pickWinnerStrategy.pick(participantList, request.getEventWinners());
        return winnerList.stream().map(ParticipateResponse::from).toList();
    }

    @Override
    public PageResponse<ParticipateResponse> getParticipateHistoryByMember(String email, PageRequest request) {
        Page<Participant> pages = participantRepository.getParticipantsByMember(request.getPageable(), email);
        List<ParticipateResponse> content = pages.getContent().stream().map(ParticipateResponse::from).toList();
        return PageResponse.of(request, content, pages.getTotalPages());
    }

    @Override
    public List<ParticipateResponse> getParticipantsByEvent(Long id) {
        return participantRepository.getParticipantsByEvent(id).stream().map(ParticipateResponse::from).toList();
    }

    @Override
    public ParticipateResponse getParticipant(Long id, String email) {
        return ParticipateResponse.from(getExistParticipant(id, email));
    }

    @Override
    public List<ParticipateResponse> setParticipantsStatusWait(Long id) {
        List<Participant> list = participantRepository.getParticipantsByEvent(id);
        for (Participant participant : list) {
            participant.setStatus(ParticipantStatus.WAIT_RESULT);
            participantRepository.save(participant);
        }
        return list.stream().map(ParticipateResponse::from).toList();
    }

    @Override
    public int getParticipantCount(Long id) {
        return participantRepository.getParticipantsByEvent(id).size();
    }
    public ParticipantServiceImpl(@NotNull EventRepository eventRepository, @NotNull MemberRepository memberRepository, @NotNull ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
        this.participantRepository = participantRepository;
    }
}
