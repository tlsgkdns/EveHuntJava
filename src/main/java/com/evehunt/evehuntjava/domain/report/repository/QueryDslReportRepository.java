package com.evehunt.evehuntjava.domain.report.repository;

import com.evehunt.evehuntjava.domain.report.model.Report;
import com.evehunt.evehuntjava.global.common.page.PageRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

public interface QueryDslReportRepository {
    @NotNull
    Page<Report> searchReport(@NotNull PageRequest pageRequest);
}