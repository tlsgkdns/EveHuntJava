package com.evehunt.evehuntjava.domain.event.service;

import com.evehunt.evehuntjava.domain.event.dto.*;
import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.event.repository.EventRepository;
import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.member.repository.MemberRepository;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.ModelNotFoundException;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.List;

@Service
public class EventEntityServiceImpl implements EventEntityService{
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    private Event getExistEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ModelNotFoundException("Event", String.valueOf(eventId)));
    }

    @CacheEvict(
            cacheNames = {"eventList"}
    )
    @Transactional
    @NotNull
    public EventResponse editEvent(@Nullable Long eventId, @NotNull EventEditRequest eventEditRequest) {
        Event event = this.getExistEvent(eventId);
        if (eventEditRequest.getTitle() != null) {
            event.setTitle(eventEditRequest.getTitle());
        }
        if (eventEditRequest.getWinMessage() != null) {
            event.setWinMessage(eventEditRequest.getWinMessage());
        }
        if(eventEditRequest.getEventImage() != null) {
            event.setImage(Image.from(eventEditRequest.getEventImage()));
        }
        if(eventEditRequest.getDescription() != null){
            event.setDescription(eventEditRequest.getDescription());
        }
        if(eventEditRequest.getQuestion() != null) {
            event.setQuestion(eventEditRequest.getQuestion());
        }
        if(eventEditRequest.getCloseAt() != null) {
            event.setCloseAt(eventEditRequest.getCloseAt().atZone(ZoneId.of("Asia/Seoul")));
        }
        if(eventEditRequest.getCapacity() != null) {
            event.setCapacity(eventEditRequest.getCapacity());
        }
        return EventResponse.from(eventRepository.save(event));
    }

    @CacheEvict(
            cacheNames = {"eventList", "popularEvents"}
    )
    @Transactional
    @NotNull
    public EventResponse hostEvent(@NotNull EventHostRequest eventHostRequest, @NotNull String username) {

        Member member = memberRepository.findMemberByEmail(username);
        Event event = eventRepository.save(eventHostRequest.to(member));
        return EventResponse.from(event);
    }

    @Transactional
    @NotNull
    public EventResponse getEvent(@Nullable Long eventId) {
        return EventResponse.from(getExistEvent(eventId));
    }

    @Cacheable(
            cacheManager = "cacheManager",
            cacheNames = {"eventList"},
            key = "#pageRequest.searchType + #pageRequest.keyword + #pageRequest.sortType + #pageRequest.asc",
            condition = "#pageRequest.page == 1"
    )
    @Transactional
    @NotNull
    public PageResponse<EventCardResponse> getEvents(@NotNull PageRequest pageRequest) {
        Page<EventCardResponse> eventPages = eventRepository.searchEvents(pageRequest);
        return PageResponse.of(pageRequest, eventPages.toList(), (int)eventPages.getTotalElements());
    }

    @Transactional
    @NotNull
    public EventResponse announceEvent(@Nullable Long eventId) {
        Event event = this.getExistEvent(eventId);
        event.setEventStatus(EventStatus.ANNOUNCED);
        return EventResponse.from(eventRepository.save(event));
    }

    @Transactional
    @NotNull
    public List<EventIdResponse> setExpiredEventsClose() {
        return eventRepository.setExpiredEventsClosed();
    }

    @NotNull
    public EventResponse closeEvent(@Nullable Long eventId) {
        Event event = this.getExistEvent(eventId);
        event.setEventStatus(EventStatus.CLOSED);
        return EventResponse.from(eventRepository.save(event));
    }

    @Transactional
    @NotNull
    public List<EventCardResponse> getPopularEvent() {
        return eventRepository.getPopularEvents();
    }

    public EventEntityServiceImpl(@NotNull EventRepository eventRepository, @NotNull MemberRepository memberRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
    }
}
