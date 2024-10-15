package com.evehunt.evehuntjava.domain.event.dto;

import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor(force = true)
public class EventEditRequest {
    private final String title;

    private final String description;

    private final String winMessage;

    private final String eventImage;

    private final Integer capacity;

    private final String question;

    private final LocalDateTime closeAt;

    private final List<TagAddRequest> tagAddRequests;


    public final String getTitle() {
        return this.title;
    }


    public final String getDescription() {
        return this.description;
    }


    public final String getWinMessage() {
        return this.winMessage;
    }


    public final String getEventImage() {
        return this.eventImage;
    }


    public final Integer getCapacity() {
        return this.capacity;
    }


    public final String getQuestion() {
        return this.question;
    }


    public final LocalDateTime getCloseAt() {
        return this.closeAt;
    }


    public final List<TagAddRequest> getTagAddRequests() {
        return this.tagAddRequests;
    }

    public EventEditRequest(String title, String description, String winMessage, String eventImage, Integer capacity, String question,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeAt,  List<TagAddRequest> tagAddRequests) {
        this.title = title;
        this.description = description;
        this.winMessage = winMessage;
        this.eventImage = eventImage;
        this.capacity = capacity;
        this.question = question;
        this.closeAt = closeAt;
        this.tagAddRequests = tagAddRequests;
    }
}
