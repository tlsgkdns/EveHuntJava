package com.evehunt.evehuntjava.domain.report.api;

import com.evehunt.evehuntjava.domain.report.dto.ReportHandleRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportResponse;
import com.evehunt.evehuntjava.domain.report.service.ReportService;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/reports"})
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @NotNull
    public ResponseEntity<ReportResponse> createReport(@RequestBody @NotNull ReportRequest reportRequest, @AuthenticationPrincipal @NotNull UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.createReport(reportRequest, userDetails.getUsername()));
    }

    @GetMapping
    @NotNull
    public ResponseEntity<PageResponse<ReportResponse>> getReports(@NotNull PageRequest pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getReports(pageRequest));
    }

    @PatchMapping({"/{reportId}"})
    @NotNull
    public ResponseEntity<ReportResponse> processReport(@PathVariable long reportId, @RequestBody @NotNull ReportHandleRequest reportHandleRequest) {
       return ResponseEntity.status(HttpStatus.CREATED).body(reportService.handleReport(reportId, reportHandleRequest));
    }

    public ReportController(@NotNull ReportService reportService) {
        this.reportService = reportService;
    }
}
