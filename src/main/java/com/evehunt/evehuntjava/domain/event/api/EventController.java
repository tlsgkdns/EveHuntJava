package com.evehunt.evehuntjava.domain.event.api;

import com.evehunt.evehuntjava.domain.event.dto.EventCardResponse;
import com.evehunt.evehuntjava.domain.event.dto.EventEditRequest;
import com.evehunt.evehuntjava.domain.event.dto.EventHostRequest;
import com.evehunt.evehuntjava.domain.event.dto.EventResponse;
import com.evehunt.evehuntjava.domain.event.service.EventService;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.InvalidRequestException;
import com.evehunt.evehuntjava.global.infra.aop.annotation.CheckEventLoginMember;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public void checkBindingResult(@NotNull BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           StringBuilder messages = new StringBuilder();
           List<String> list = bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
           for(int i = 0; i < list.size(); i++)
           {
               messages.append(list.get(i));
               if(i != list.size() - 1)
                   messages.append('\n');
           }
            throw new InvalidRequestException("Event", messages.toString());
        }
    }

    @GetMapping({"/{eventId}"})
    @NotNull
    public ResponseEntity<EventResponse> getEvent(@PathVariable long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEvent(eventId));
    }

    @GetMapping
    @NotNull
    public ResponseEntity<PageResponse<EventCardResponse>> getEvents(@NotNull PageRequest pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEvents(pageRequest));
    }

    @PostMapping
    @NotNull
    public ResponseEntity<EventResponse> hostEvent(@RequestBody @Valid @NotNull EventHostRequest eventHostRequest,
                                    @NotNull BindingResult bindingResult, @AuthenticationPrincipal @NotNull UserDetails userDetails) {
        checkBindingResult(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.hostEvent(eventHostRequest, userDetails.getUsername()));
    }

    @CheckEventLoginMember
    @PatchMapping({"/{eventId}"})
    public ResponseEntity<EventResponse> editEvent(@PathVariable long eventId, @RequestBody @Valid @NotNull EventEditRequest eventEditRequest, @NotNull BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return ResponseEntity.status(HttpStatus.OK).body(eventService.editEvent(eventId, eventEditRequest));
    }

    @PatchMapping({"/{eventId}/close"})
    @NotNull
    public ResponseEntity<Object> closeEvent(@PathVariable long eventId) {
        eventService.closeEvent(eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping({"/{eventId}/tags"})
    @NotNull
    public ResponseEntity<List<TagResponse>> getTags(@PathVariable long eventId) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getTags(eventId));
    }

    @GetMapping({"/popular"})
    @NotNull
    public ResponseEntity<List<EventCardResponse>> getPopularEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getPopularEvent());
    }

    public EventController(@NotNull EventService eventService) {
        this.eventService = eventService;
    }
}
