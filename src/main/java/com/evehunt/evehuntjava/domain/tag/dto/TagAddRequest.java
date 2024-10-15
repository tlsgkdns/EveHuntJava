package com.evehunt.evehuntjava.domain.tag.dto;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.tag.model.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class TagAddRequest {
    private final String tagName;

    public final Tag to(Event event) {
        return new Tag(event, this.tagName);
    }

    @NotNull
    public final String getTagName() {
        return this.tagName;
    }

    public TagAddRequest(String tagName) {
        this.tagName = tagName;
    }
}
