package com.evehunt.evehuntjava.domain.participant.repository;

import com.evehunt.evehuntjava.domain.participant.model.Participant;
import com.evehunt.evehuntjava.domain.participant.model.QParticipant;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class QueryDslParticipantRepositoryImpl extends QuerydslRepositorySupport implements QueryDslParticipantRepository {

    QParticipant participant = QParticipant.participant1;
    public QueryDslParticipantRepositoryImpl() {
        super(Participant.class);
    }

    @Override
    public Participant getParticipant(Long id, String email) {
        BooleanBuilder query = new BooleanBuilder();
        query.and(participant.event.id.eq(id));
        query.and(participant.participant.email.eq(email));
        return from(participant).where(query).fetchOne();
    }

    @Override
    public List<Participant> getParticipantsByEvent(Long id) {
        if (id == null) return new ArrayList<>();
        return from(participant).where(participant.event.id.eq(id)).fetch();
    }

    @Override
    public Page<Participant> getParticipantsByMember(Pageable request, String email) {
        List<Participant> content = from(participant)
                .where(participant.participant.email.eq(email))
                .offset(request.getOffset())
                .limit(request.getPageSize())
                .fetch();
        return new PageImpl<Participant>(content, request,
                from(participant).select(participant.count()).where(participant.participant.email.eq(email)).fetchOne());
    }
}
