package com.evehunt.evehuntjava.domain.report.dto;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.report.model.Report;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class ReportRequest {
    private final Long eventId;
    @NotNull
    private final String reason;

    @NotNull
    public final Report to(@NotNull Member member, @NotNull Event event) {
        return new Report(member, event, this.reason);
    }

    public final Long getEventId() {
        return this.eventId;
    }

    @NotNull
    public final String getReason() {
        return this.reason;
    }

    public ReportRequest(Long eventId, @NotNull String reason) {
        this.eventId = eventId;
        this.reason = reason;
    }
}
