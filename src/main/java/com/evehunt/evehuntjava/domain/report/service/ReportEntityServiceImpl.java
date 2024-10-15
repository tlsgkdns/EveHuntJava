package com.evehunt.evehuntjava.domain.report.service;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.repository.EventRepository;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.member.repository.MemberRepository;
import com.evehunt.evehuntjava.domain.report.dto.ReportHandleRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportResponse;
import com.evehunt.evehuntjava.domain.report.model.Report;
import com.evehunt.evehuntjava.domain.report.repository.ReportRepository;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import com.evehunt.evehuntjava.global.exception.exception.AlreadyExistModelException;
import com.evehunt.evehuntjava.global.exception.exception.ModelNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReportEntityServiceImpl implements ReportEntityService {
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    @NotNull
    public Member getExistMember(@NotNull String username) {
        Member member = memberRepository.findMemberByEmail(username);
        if(member == null) throw new ModelNotFoundException("Member", username);
        return member;
    }

    @NotNull
    public Event getExistEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ModelNotFoundException("Event", eventId.toString()));
    }

    @NotNull
    public Report getExistReport(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(() -> new ModelNotFoundException("Report", reportId.toString()));
    }

    @Transactional
    @NotNull
    public ReportResponse createReport(@NotNull ReportRequest reportRequest, @NotNull String username) {
        if (reportRepository.existsByReporterEmailAndEventId(username, reportRequest.getEventId())) throw new AlreadyExistModelException("Report");

        Event event = this.getExistEvent(reportRequest.getEventId());
        Member member = this.getExistMember(username);
        Report report = reportRequest.to(member, event);
        reportRepository.save(report);
        return ReportResponse.from(report);
    }

    @Transactional
    @NotNull
    public ReportResponse handleReport(long reportId, @NotNull ReportHandleRequest reportHandleRequest) {
        Report report = this.getExistReport(reportId);
        report.setProcess(reportHandleRequest.getAccepted());
        return ReportResponse.from(reportRepository.save(report));
    }

    @NotNull
    public PageResponse<ReportResponse> getReports(@NotNull PageRequest pageRequest) {
        Page<Report> reportPage = reportRepository.searchReport(pageRequest);
        List<ReportResponse> content = reportPage.getContent().stream().map(ReportResponse::from).toList();
        return PageResponse.of(pageRequest, content, (int)reportPage.getTotalElements());
    }

    public ReportEntityServiceImpl(@NotNull ReportRepository reportRepository,
                                   @NotNull MemberRepository memberRepository,
                                   @NotNull EventRepository eventRepository) {
        this.reportRepository = reportRepository;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
    }
}
