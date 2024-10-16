package com.evehunt.evehuntjava.domain.event.repository;

import com.evehunt.evehuntjava.domain.event.dto.EventCardResponse;
import com.evehunt.evehuntjava.domain.event.dto.EventIdResponse;
import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.event.model.QEvent;
import com.evehunt.evehuntjava.domain.image.model.QImage;
import com.evehunt.evehuntjava.domain.participant.model.QParticipant;
import com.evehunt.evehuntjava.domain.tag.model.QTag;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.ZonedDateTime;
import java.util.List;

public class QueryDslEventRepositoryImpl extends QuerydslRepositorySupport implements QueryDslEventRepository {
    public QueryDslEventRepositoryImpl() {
        super(Event.class);
    }
    QEvent event = QEvent.event;
    QParticipant participant = QParticipant.participant1;
    QTag tag = QTag.tag;
    QImage image = QImage.image;

    @Override
    public Page<EventCardResponse> searchEvents(PageRequest request) {
        Pageable pageable = request.getPageable();
        String keyword = request.keyword();
        JPQLQuery<EventCardResponse> query = from(event).select(Projections.constructor(
                EventCardResponse.class,
                event.id,
                event.host.name,
                event.title,
                event.capacity,
                event.closeAt,
                from(participant).select(participant.count()).where(participant.event.eq(event)),
                event.image
        )).leftJoin(event.image, image);
        if(keyword != null && !keyword.isEmpty())
        {
            switch (request.searchType().toLowerCase())
            {
                case "description" -> query.from(event).where(event.description.contains(keyword));
                case "titledescription" -> {
                    query.from(event);
                    query.where(event.title.contains(keyword).or(event.description.contains(keyword)));
                }
                case "host" -> {
                    query.from(event);
                    query.where(event.host.name.contains(keyword));
                }
                case "hostid" -> {
                    query.from(event);
                    query.where(event.host.id.eq(Long.parseLong(keyword)));
                }
                case "tag" -> {
                    query.leftJoin(tag).on(tag.event.id.eq(event.id))
                            .where(tag.tagName.eq(keyword));
                }
                case "participate" -> {
                    query.leftJoin(participant).on(participant.event.id.eq(event.id))
                            .where(participant.participant.id.eq(Long.parseLong(keyword)));
                }
                default -> {
                    query.from(event);
                    query.where(event.title.contains(keyword));
                }
            }
        }
        switch (request.searchType().toLowerCase())
        {
            case "close" -> {
            if(!request.asc()) query.orderBy(event.closeAt.desc());
            else query.orderBy(event.closeAt.desc());
            }
            case "host" -> {
            if(!request.asc()) query.orderBy(event.host.name.desc());
            else query.orderBy(event.host.name.asc());
            }
            case "title" -> {
            if(!request.asc()) query.orderBy(event.title.desc());
            else query.orderBy(event.title.asc());
            }
            default -> {
            if(!request.asc()) query.orderBy(event.createdAt.desc());
            else query.orderBy(event.createdAt.asc());
            }
        }

        return new PageImpl<>(query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch());
    }

    @Override
    public List<EventIdResponse> setExpiredEventsClosed() {
        BooleanBuilder whereClause = new BooleanBuilder(event.eventStatus.eq(EventStatus.PROCEED).and(event.closeAt.before(ZonedDateTime.now())));
        List<EventIdResponse> list = from(event).select(Projections.constructor(EventIdResponse.class, event.id)).where(whereClause).fetch();
        update(event).set(event.eventStatus, EventStatus.CLOSED).where(whereClause).execute();
        getEntityManager().clear();
        getEntityManager().flush();
        return list;
    }

    @Override
    public List<EventCardResponse> getPopularEvents() {
        NumberPath<Long> participantCount = Expressions.numberPath(Long.class, "participantCount");
        JPQLQuery<EventCardResponse> list = from(event).select(Projections.constructor(
                EventCardResponse.class,
                event.id,
                event.host.name,
                event.title,
                event.capacity,
                event.closeAt,
                ExpressionUtils.as(from(participant).select(participant.count()).where(participant.event.eq(event)), participantCount),
                event.image)).leftJoin(event.image, image).orderBy(participantCount.desc());
        if(list.fetch().size() > 5) return list.limit(5).fetch();
        return list.fetch();
    }

    //Long id, @NotNull String hostName, @NotNull String title, int capacity, @NotNull LocalDateTime closeAt, int participantCount, String eventImage
}
