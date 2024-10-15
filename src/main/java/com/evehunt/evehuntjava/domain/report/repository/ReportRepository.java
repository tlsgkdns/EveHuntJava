package com.evehunt.evehuntjava.domain.report.repository;

import com.evehunt.evehuntjava.domain.report.model.Report;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, QueryDslReportRepository {
    boolean existsByReporterEmailAndEventId(@NotNull String email, Long eventId);
}
