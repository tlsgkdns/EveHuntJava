package com.evehunt.evehuntjava.domain.report.model;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private final Long id = null;
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private final Member reporter;
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private final Event event;
    @NotNull
    private final String reason;
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReportStatus processed = ReportStatus.WAIT;

    public Report() {
        reason = "";
        event = null;
        reporter = null;
    }

    public final Long getId() {
        return this.id;
    }

    public final void setProcess(boolean accept) {
        this.processed = accept ? ReportStatus.ACCEPT : ReportStatus.REJECT;
    }

    public final Member getReporter() {
        return this.reporter;
    }
    public final Event getEvent() {
        return this.event;
    }

    @NotNull
    public final String getReason() {
        return this.reason;
    }

    @NotNull
    public final ReportStatus getProcessed() {
        return this.processed;
    }

    public final void setProcessed(@NotNull ReportStatus var1) {
        this.processed = var1;
    }

    public Report(Member reporter, Event event, @NotNull String reason) {
        this.reporter = reporter;
        this.event = event;
        this.reason = reason;
    }
}