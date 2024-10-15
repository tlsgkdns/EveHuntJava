package com.evehunt.evehuntjava.domain.event.dto;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor(force = true)
public class EventResponse {
private final Long id;
    @NotNull
    private final String title;
    @NotNull
    private final String description;
    @NotNull
    private final String winMessage;

    private final String question;

    private final String eventImage;
    private final int capacity;
    @NotNull
    private final EventStatus status;

    private final Long hostId;

    private final String hostName;
    @NotNull
    private final LocalDateTime createdAt;
    @NotNull
    private final LocalDateTime updatedAt;
    @NotNull
    private final LocalDateTime closedAt;

    private List<TagResponse> eventTags;
    private int participantCount;

    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getDescription() {
        return this.description;
    }

    @NotNull
    public final String getWinMessage() {
        return this.winMessage;
    }

    public final String getQuestion() {
        return this.question;
    }

    public final String getEventImage() {
        return this.eventImage;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    @NotNull
    public final EventStatus getStatus() {
        return this.status;
    }

    public final Long getHostId() {
        return this.hostId;
    }

    public final String getHostName() {
        return this.hostName;
    }

    @NotNull
    public final LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @NotNull
    public final LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @NotNull
    public final LocalDateTime getClosedAt() {
        return this.closedAt;
    }

    public final List<TagResponse> getEventTags() {
        return this.eventTags;
    }

    public final void setEventTags(List<TagResponse> eventTags) {
        this.eventTags = eventTags;
    }

    public final int getParticipantCount() {
        return this.participantCount;
    }

    public final void setParticipantCount(int count) {
        this.participantCount = count;
    }

    public EventResponse(Long id, @NotNull String title, @NotNull String description, @NotNull String winMessage, String question, String eventImage, int capacity, @NotNull EventStatus status, Long hostId,
                         String hostName, @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt,
                         @NotNull LocalDateTime closedAt, List<TagResponse> eventTags, int participantCount) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.winMessage = winMessage;
        this.question = question;
        this.eventImage = eventImage;
        this.capacity = capacity;
        this.status = status;
        this.hostId = hostId;
        this.hostName = hostName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.eventTags = eventTags;
        this.participantCount = participantCount;
    }

    public static EventResponse from(@NotNull Event event) {
        return new EventResponse(event.getId(), event.getTitle(), event.getDescription(), event.getWinMessage(), event.getQuestion(),
                event.getImage() != null ? event.getImage().getLink() : null, event.getCapacity(),
                event.getEventStatus(), event.getHost().getId(), event.getHost().getName(), event.getCreatedAt().toLocalDateTime(), event.getUpdatedAt().toLocalDateTime(), event.getCloseAt().toLocalDateTime(),
                null, 0);
    }
}
