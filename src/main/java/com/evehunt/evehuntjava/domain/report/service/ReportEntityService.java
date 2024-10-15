package com.evehunt.evehuntjava.domain.report.service;

import com.evehunt.evehuntjava.domain.report.dto.ReportHandleRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

public interface ReportEntityService {
    @NotNull
    ReportResponse createReport(@NotNull ReportRequest reportRequest, @NotNull String username);

    @NotNull
    PageResponse<ReportResponse> getReports(@NotNull PageRequest request);

    @NotNull
    ReportResponse handleReport(long reportId, @NotNull ReportHandleRequest request);
}