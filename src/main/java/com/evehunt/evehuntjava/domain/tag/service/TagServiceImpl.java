package com.evehunt.evehuntjava.domain.tag.service;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.repository.EventRepository;
import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import com.evehunt.evehuntjava.domain.tag.dto.TagResponse;
import com.evehunt.evehuntjava.domain.tag.model.Tag;
import com.evehunt.evehuntjava.domain.tag.repository.TagRepository;
import com.evehunt.evehuntjava.global.exception.exception.InvalidModelException;
import com.evehunt.evehuntjava.global.exception.exception.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService{
    private final EventRepository eventRepository;
    private final TagRepository tagRepository;
    private final Integer tagCapacity;
    private Event getExistEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ModelNotFoundException("Event", String.valueOf(eventId)));
    }
    private Tag getExistTag(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new ModelNotFoundException("Event", String.valueOf(tagId)));
    }
    @Override
    public List<TagResponse> getTags(Long id) {
        return tagRepository.getTagsByEvent(id).stream().map(TagResponse::from).toList();
    }

    @Transactional
    @Override
    public TagResponse addTag(Long eventId, TagAddRequest request) {
        Event event = getExistEvent(eventId);
        if(getTags(eventId).size() >= tagCapacity) throw new InvalidModelException("Tag");
        Tag tag = tagRepository.save(request.to(event));
        return TagResponse.from(tag);
    }

    @Transactional
    @Override
    public void deleteTags(Long eventId) {
        tagRepository.deleteTagsByEvent(eventId);
    }

    @Transactional
    @Override
    public void deleteTag(Long eventId, Long tagId) {
        getExistEvent(eventId);
        Tag tag = getExistTag(tagId);
        tagRepository.delete(tag);
    }

    @Override
    @Cacheable(cacheManager = "cacheManager", cacheNames = {"eventTags"}, key = "2")
    public List<TagResponse> getPopularTags() {
        return tagRepository.getPopularTags().stream().map(TagResponse::from).toList();
    }

     public TagServiceImpl(EventRepository eventRepository, TagRepository tagRepository, @Value("${event.tag.capacity}") Integer tagCapacity)
     {
         this.eventRepository = eventRepository;
         this.tagRepository = tagRepository;
         this.tagCapacity = tagCapacity;
     }
}
