package com.evehunt.evehuntjava.global.exception.dto;

import jakarta.validation.constraints.NotNull;

public class ErrorResponse {
    @NotNull
    private final String message;
    private final long errorCode;

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    public final long getErrorCode() {
        return this.errorCode;
    }

    public ErrorResponse(@NotNull String message, long errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
