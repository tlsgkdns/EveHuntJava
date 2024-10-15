package com.evehunt.evehuntjava.domain.tag.repository;

import com.evehunt.evehuntjava.domain.tag.model.Tag;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface QueryDslTagRepository {
    @NotNull
    List<Tag> getTagsByEvent(Long eventId);

    void deleteTagsByEvent(Long eventId);

    int getCountOfTags(@NotNull String tagName);

    @NotNull
    List<Tag> getPopularTags();
}
