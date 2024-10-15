package com.evehunt.evehuntjava.domain.tag.dto;

import com.evehunt.evehuntjava.domain.tag.model.Tag;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class TagResponse {
    private final String tagName;
    public final String getTagName() {
        return this.tagName;
    }

    public TagResponse(String tagName) {
        this.tagName = tagName;
    }

    public static TagResponse from(Tag tag)
    {
        return new TagResponse(tag.getTagName());
    }
}
