package com.evehunt.evehuntjava.global.exception.exception;

import com.evehunt.evehuntjava.global.exception.ErrorCode;
import com.evehunt.evehuntjava.global.exception.dto.ErrorResponse;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CustomException extends RuntimeException {
    @NotNull
    private final Logger logger;
    @Nullable
    private final String message;
    @NotNull
    private final ErrorCode errorCode;

    @NotNull
    public Logger getLogger() {
        return this.logger;
    }

    public void log() {
    }
    public CustomException(@Nullable String message, @NotNull ErrorCode errorCode) {

        this.message = message;
        this.errorCode = errorCode;
        this.logger = LoggerFactory.getLogger(this.getClass());
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    @Override
    @Nullable
    public String getMessage() {
        return message;
    }
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse((getMessage() == null) ? getErrorCode().getMessage() : getMessage(),getErrorCode().getCode());
    }
}
