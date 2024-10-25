package com.evehunt.evehuntjava.domain.report.repository;

import com.evehunt.evehuntjava.domain.report.model.QReport;
import com.evehunt.evehuntjava.domain.report.model.Report;
import com.evehunt.evehuntjava.domain.report.model.ReportStatus;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QueryDslReportRepositoryImpl extends QuerydslRepositorySupport implements QueryDslReportRepository {
    public QueryDslReportRepositoryImpl() {
        super(Report.class);
    }
    QReport report = QReport.report;
    @Override
    public Page<Report> searchReport(PageRequest pageRequest) {
        Pageable pageable = pageRequest.getPageable();
        BooleanBuilder whereClause = new BooleanBuilder(report.processed.eq(ReportStatus.WAIT));
        OrderSpecifier orderBy = null;
        switch (pageRequest.sortType().toLowerCase())
        {
            case "reporter": {
                if(!pageRequest.asc()) orderBy = report.reporter.name.desc();
                else orderBy = report.reporter.name.asc();
            }
            case "event" : {
                if(!pageRequest.asc()) orderBy = report.event.title.desc();
                else orderBy = report.event.title.asc();
            }
            default: {
                if(!pageRequest.asc()) orderBy = report.createdAt.desc();
                else orderBy = report.createdAt.asc();
            }
        }
        if(!pageRequest.keyword().isEmpty())
        {
            switch (pageRequest.searchType().toLowerCase())
            {
                case "reporter" : {
                    whereClause.or(report.reporter.name.contains(pageRequest.keyword()));
                }
                case "event" : {
                    whereClause.or(report.event.title.contains(pageRequest.keyword()));
                }
                default:  {
                    whereClause.or(report.reason.contains(pageRequest.keyword()));
                }
            }
        }
        JPQLQuery<Report> query = from(report)
                .where(whereClause)
                .orderBy(orderBy);
        long cnt = query.fetch().size();
        return new PageImpl<>(query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch(), pageable, cnt);
    }
}
