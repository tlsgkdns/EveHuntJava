package com.evehunt.evehuntjava.domain.tag.repository;

import com.evehunt.evehuntjava.domain.tag.model.QTag;
import com.evehunt.evehuntjava.domain.tag.model.Tag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QueryDslTagRepositoryImpl extends QuerydslRepositorySupport implements QueryDslTagRepository {
    private final QTag tag = QTag.tag;

    public QueryDslTagRepositoryImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> getTagsByEvent(Long eventId) {
        return from(tag).where(tag.event.id.eq(eventId)).fetch();
    }

    @Override
    public void deleteTagsByEvent(Long eventId) {
        delete(tag).where(tag.event.id.eq(eventId)).execute();
    }

    @Override
    public int getCountOfTags(String tagName) {
        return from(tag).where(tag.tagName.eq(tagName)).fetch().size();
    }

    @Override
    public List<Tag> getPopularTags() {
        return from(tag).select(tag.tagName, tag.tagName.count())
                .groupBy(tag.tagName)
                .orderBy(tag.tagName.count().desc())
                .offset(0)
                .limit(10)
                .fetch()
                .stream().map(t -> new Tag(t.get(tag.event), t.get(tag.tagName))).toList();
    }
}
