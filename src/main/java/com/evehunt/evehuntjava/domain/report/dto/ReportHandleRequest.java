package com.evehunt.evehuntjava.domain.report.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
public class ReportHandleRequest {
    private final boolean accepted;
    private final int suspendDay;

    public final boolean getAccepted() {
        return this.accepted;
    }

    public final int getSuspendDay() {
        return this.suspendDay;
    }

    public ReportHandleRequest(boolean accepted, int suspendDay) {
        this.accepted = accepted;
        this.suspendDay = suspendDay;
    }
}
