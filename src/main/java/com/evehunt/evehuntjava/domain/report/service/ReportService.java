package com.evehunt.evehuntjava.domain.report.service;

import com.evehunt.evehuntjava.domain.report.dto.ReportHandleRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportRequest;
import com.evehunt.evehuntjava.domain.report.dto.ReportResponse;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import com.evehunt.evehuntjava.global.common.page.PageResponse;
import jakarta.validation.constraints.NotNull;

public interface ReportService {
    @NotNull
    ReportResponse createReport(@NotNull ReportRequest var1, @NotNull String var2);

    @NotNull
    PageResponse<ReportResponse> getReports(@NotNull PageRequest var1);

    @NotNull
    ReportResponse handleReport(long var1, @NotNull ReportHandleRequest var3);
}
