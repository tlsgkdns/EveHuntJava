package com.evehunt.evehuntjava.domain.event.service;

import com.evehunt.evehuntjava.domain.event.dto.*;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.mail.dto.MailRequest;
import com.evehunt.evehuntjava.domain.mail.service.MailService;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipantResponse;
import com.evehunt.evehuntjava.domain.participant.model.ParticipantStatus;
import com.evehunt.evehuntjava.domain.participant.service.ParticipantService;
import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import com.evehunt.evehuntjava.domain.tag.service.TagService;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.InvalidModelException;
import com.evehunt.evehuntjava.global.infra.aop.annotation.CheckEventLoginMember;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    private final EventEntityService eventEntityService;
    private final MemberService memberService;
    private final MailService mailService;
    private final ParticipantService participantService;
    private final TagService tagService;
    private final String resultTitleMessage = "이벤트 결과 안내드립니다.";

    private final String resultLoseMessage(String title) {
        return title + "에 당첨되지 않았습니다.";
    }
    @Override
    public EventResponse editEvent(Long eventId, EventEditRequest request) {
        tagService.deleteTags(eventId);
        for(TagAddRequest tagAddRequest : request.getTagAddRequests())
        {
            tagService.addTag(eventId, tagAddRequest);
        }
        return eventEntityService.editEvent(eventId, request);
    }

    @Override
    public EventResponse hostEvent(EventHostRequest request, String email) {
        EventResponse event = eventEntityService.hostEvent(request, email);
        if(request.getTagAddRequests() != null)
        {
            for(TagAddRequest tagAddRequest : request.getTagAddRequests())
            {
                tagService.addTag(event.getId(), tagAddRequest);
            }
        }
        return event;
    }

    @Override
    public EventResponse getEvent(Long eventId) {
        EventResponse eventResponse = eventEntityService.getEvent(eventId);
        eventResponse.setEventTags(tagService.getTags(eventId));
        eventResponse.setParticipantCount(participantService.getParticipantCount(eventId));
        return eventResponse;
    }

    @Override
    public PageResponse<EventCardResponse> getEvents(PageRequest request) {
        return eventEntityService.getEvents(request);
    }

    @Override
    public List<EventIdResponse> setExpiredEventsClose() {
        List<EventIdResponse> expiredEvents = eventEntityService.setExpiredEventsClose();
        for(EventIdResponse id: expiredEvents)
        {
            participantService.setParticipantsStatusWait(id.getId());
        }
        return expiredEvents;
    }
    @Transactional

    @Override
    public ParticipantResponse participateEvent(Long eventId, String email, ParticipantRequest request) {
        EventResponse event = getEvent(eventId);
        if(event.getStatus() != EventStatus.PROCEED) throw new InvalidModelException("Event");
        ParticipantResponse participant = participantService.participateEvent(eventId, email, request);
        if(event.getCapacity() == participantService.getParticipantCount(eventId))
            eventEntityService.closeEvent(eventId);
        return participant;
    }

    @Override
    public void resignEventParticipate(Long eventId, String email) {
        participantService.resignEventParticipate(eventId, email);
    }

    @Override
    public List<EventCardResponse> getPopularEvent() {
        return eventEntityService.getPopularEvent();
    }

    @CheckEventLoginMember
    @Override
    public List<ParticipantResponse> setEventResult(Long eventId, EventWinnerRequest request) {
        List<ParticipantResponse> list = participantService.setEventResult(eventId, request);
        String title = eventEntityService.getEvent(eventId).getTitle();
        for(ParticipantResponse participant : list)
        {
            String email = memberService.getMember(participant.getMemberId()).getEmail();
            String resultMessage = "";
            if(participant.getStatus() == ParticipantStatus.LOSE)
                resultMessage = resultLoseMessage(title);
            else
                resultMessage = request.getWinMessages().get(request.getEventWinners().indexOf(participant.getId()));
            mailService.addMail(new MailRequest(email, resultTitleMessage, resultMessage));
        }
        return list;
    }

    @Override
    public List<ParticipantResponse> getParticipants(Long eventId) {
        return participantService.getParticipantsByEvent(eventId);
    }

    @Override
    public ParticipantResponse getParticipant(Long eventId, String email) {
        return participantService.getParticipant(eventId, email);
    }

    @Override
    public List<TagResponse> getTags(Long eventId) {
        return tagService.getTags(eventId);
    }

    @Override
    public void closeEvent(Long eventId) {
        eventEntityService.closeEvent(eventId);
    }

    @Override
    public TagResponse addTag(Long eventId, TagAddRequest request) {
        return tagService.addTag(eventId, request);
    }

    @Override
    public void deleteTag(Long eventId, Long tagId) {
        tagService.deleteTag(eventId, tagId);
    }
    public EventServiceImpl(@NotNull EventEntityService eventEntityService, @NotNull MemberService memberService,
                            @NotNull MailService mailService, @NotNull ParticipantService participantService, @NotNull TagService tagService) {
        this.eventEntityService = eventEntityService;
        this.memberService = memberService;
        this.mailService = mailService;
        this.participantService = participantService;
        this.tagService = tagService;
    }
}
