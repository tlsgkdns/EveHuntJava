package com.evehunt.evehuntjava.domain.report.service;

import com.evehunt.evehuntjava.domain.event.service.EventService;
import com.evehunt.evehuntjava.domain.member.service.MemberService;
import com.evehunt.evehuntjava.domain.report.dto.ReportHandleRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportEntityService reportEntityService;
    private final EventService eventService;
    private final MemberService memberService;

    @Transactional
    @NotNull
    public ReportResponse createReport(@NotNull ReportRequest reportRequest, @NotNull String username) {
        return reportEntityService.createReport(reportRequest, username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @NotNull
    public PageResponse<ReportResponse> getReports(@NotNull PageRequest pageRequest) {
        return reportEntityService.getReports(pageRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    @NotNull
    public ReportResponse handleReport(long reportId, @NotNull ReportHandleRequest reportHandleRequest) {
        ReportResponse reportResponse = reportEntityService.handleReport(reportId, reportHandleRequest);
        if(reportHandleRequest.getAccepted())
        {
            eventService.closeEvent(reportResponse.getEventId());
            memberService.suspendMember(eventService.getEvent(reportResponse.getEventId()).getHostId(), reportHandleRequest.getSuspendDay());
        }
        return reportResponse;
    }

    public ReportServiceImpl(@NotNull ReportEntityService reportEntityService, @NotNull EventService eventService,
                             @NotNull MemberService memberService) {
        this.reportEntityService = reportEntityService;
        this.eventService = eventService;
        this.memberService = memberService;
    }
}
