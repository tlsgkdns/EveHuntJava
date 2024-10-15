package com.evehunt.evehuntjava.domain.tag.service;

import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface TagService {
    @NotNull
    List<TagResponse> getTags(Long id);

    @NotNull
    TagResponse addTag(Long eventId, @NotNull TagAddRequest request);

    void deleteTags(Long eventId);

    void deleteTag(Long eventId, Long tagId);

    @NotNull
    List<TagResponse> getPopularTags();
}