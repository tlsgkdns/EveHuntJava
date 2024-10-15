package com.evehunt.evehuntjava.global.exception;

import com.evehunt.evehuntjava.global.exception.dto.ErrorResponse;
import com.evehunt.evehuntjava.global.exception.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    ResponseEntity<ErrorResponse> customExceptionHandler(CustomException e)
    {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorResponse());
    }
}
