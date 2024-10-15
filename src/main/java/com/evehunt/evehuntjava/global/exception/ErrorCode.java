package com.evehunt.evehuntjava.global.exception;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    MODEL_NOT_FOUND(1001, HttpStatus.NOT_FOUND, "해당 Model을 찾을 수 없습니다."),
    INVALID_IMAGE_NAME(1002, HttpStatus.BAD_REQUEST, "유효하지 않은 Image입니다."),
    ALREADY_EXIST_MODEL(1003, HttpStatus.BAD_REQUEST, "이미 존재합니다."),
    INVALID_MODEL(1004, HttpStatus.BAD_REQUEST, "유효하지 않은 Model입니다."),
    UNMATCHED_VALUE(1006, HttpStatus.BAD_REQUEST, "값이 일치하지 않습니다."),
    INVALID_REQUEST(1007, HttpStatus.BAD_REQUEST, "유효한 값이 아닙니다."),
    UNAUTHORIZED_ACCESS(2001, HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    UNMATCHED_PASSWORD(2002, HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    REDIS_LOCK_TIMEOUT(3001, HttpStatus.REQUEST_TIMEOUT, "Redis에서 Lock을 획득 실패했습니다.");

    private final long code;
    @NotNull
    private final HttpStatus httpStatus;
    @NotNull
    private final String message;

    public final long getCode() {
        return this.code;
    }

    @NotNull
    public final HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @NotNull
    public final String getMessage() {
        return this.message;
    }

    private ErrorCode(long code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}