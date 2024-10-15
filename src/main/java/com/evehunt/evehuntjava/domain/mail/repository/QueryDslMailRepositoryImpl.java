package com.evehunt.evehuntjava.domain.mail.repository;

import com.evehunt.evehuntjava.domain.mail.model.Mail;
import com.evehunt.evehuntjava.domain.mail.model.QMail;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class QueryDslMailRepositoryImpl extends QuerydslRepositorySupport implements QueryDslMailRepository {

    QMail mail = QMail.mail;
    private final Long batchCount = 50L;

    public QueryDslMailRepositoryImpl() {
        super(Mail.class);
    }

    @Override
    public List<Mail> getUnsentMails() {
        JPQLQuery<Mail> query = from(mail).where(mail.sent.eq(false));
        if(query.fetch().size() < batchCount) return query.fetch();
        return query.limit(batchCount).fetch();
    }
}
