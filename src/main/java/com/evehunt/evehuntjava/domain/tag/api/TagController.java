package com.evehunt.evehuntjava.domain.tag.api;

import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import com.evehunt.evehuntjava.domain.tag.service.TagService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/tags"})
public class TagController {
    private final TagService tagService;

    @GetMapping({"/popular"})
    @NotNull
    public ResponseEntity<List<TagResponse>> getPopularTags() {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getPopularTags());
    }

    public TagController(@NotNull TagService tagService) {
        this.tagService = tagService;
    }
}
