package com.evehunt.evehuntjava.domain.event.dto;

import com.evehunt.evehuntjava.domain.image.model.Image;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
@NoArgsConstructor(force = true)
public class EventCardResponse {
    private final Long id;
    @NotNull
    private final String hostName;
    @NotNull
    private final String title;
    private final int capacity;
    @NotNull
    private final LocalDateTime closeAt;
    private final int participantCount;
    private final String eventImage;

    public final Long getId() {
        return this.id;
    }

    @NotNull
    public final String getHostName() {
        return this.hostName;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getCapacity() {
        return this.capacity;
    }

    @NotNull
    public final LocalDateTime getCloseAt() {
        return this.closeAt;
    }

    public final int getParticipantCount() {
        return this.participantCount;
    }

    public final String getEventImage() {
        return this.eventImage;
    }

    public EventCardResponse(Long id, @NotNull String hostName, @NotNull String title, Integer capacity, @NotNull ZonedDateTime closeAt, Long participantCount, Image eventImage) {
        this.id = id;
        this.hostName = hostName;
        this.title = title;
        this.capacity = capacity;
        this.closeAt = closeAt.toLocalDateTime();
        this.participantCount = participantCount.intValue();
        this.eventImage = (eventImage == null) ? null : eventImage.getLink();
    }
}
