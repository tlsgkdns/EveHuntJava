package com.evehunt.evehuntjava.domain.report.dto;

import com.evehunt.evehuntjava.domain.report.model.Report;
import com.evehunt.evehuntjava.domain.report.model.ReportStatus;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class ReportResponse {
    private final Long reportId;
    private final String eventTitle;
    private final String reporterName;
    private final Long eventId;
    private final Long reporterId;
    private final String reason;
    private final ReportStatus processed;

    public final Long getReportId() {
        return this.reportId;
    }

    public final String getEventTitle() {
        return this.eventTitle;
    }

    public final String getReporterName() {
        return this.reporterName;
    }

    public final Long getEventId() {
        return this.eventId;
    }


    public final Long getReporterId() {
        return this.reporterId;
    }

    @NotNull
    public final String getReason() {
        return this.reason;
    }

    @NotNull
    public final ReportStatus getProcessed() {
        return this.processed;
    }

    public ReportResponse(Long reportId, String eventTitle, String reporterName,
                          Long eventId, Long reporterId, @NotNull String reason, @NotNull ReportStatus processed) {
        this.reportId = reportId;
        this.eventTitle = eventTitle;
        this.reporterName = reporterName;
        this.eventId = eventId;
        this.reporterId = reporterId;
        this.reason = reason;
        this.processed = processed;
    }
    public static ReportResponse from(@NotNull Report report) {

        return new ReportResponse(report.getId(), report.getEvent().getTitle(),
                report.getReporter().getName(), report.getEvent().getId(), report.getReporter().getId(),
                report.getReason(), report.getProcessed());
    }
}
