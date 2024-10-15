package com.evehunt.evehuntjava.domain.participant.api;


import com.evehunt.evehuntjava.domain.event.service.EventService;
import com.evehunt.evehuntjava.domain.participant.dto.EventWinnerRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateRequest;
import com.evehunt.evehuntjava.domain.participant.dto.ParticipateResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/events/{eventId}/participants")
@RestController
public class ParticipantController {
    private final EventService eventService;

    @GetMapping
    @NotNull
    public ResponseEntity<List<ParticipateResponse>> getParticipant(@PathVariable long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getParticipants(eventId));
    }

    @PostMapping
    @NotNull
    public ResponseEntity<ParticipateResponse> participateEvents(@PathVariable long eventId, @AuthenticationPrincipal @NotNull UserDetails user, @RequestBody @NotNull ParticipateRequest participateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.participateEvent(eventId, user.getUsername(), participateRequest));
    }


    @PatchMapping({"/result"})
    @NotNull
    public ResponseEntity<List<ParticipateResponse>> setEventResult(@PathVariable long eventId, @RequestBody @NotNull EventWinnerRequest eventWinnerRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.setEventResult(eventId, eventWinnerRequest));
    }

    public ParticipantController(@NotNull EventService eventService) {
        this.eventService = eventService;
    }
}
