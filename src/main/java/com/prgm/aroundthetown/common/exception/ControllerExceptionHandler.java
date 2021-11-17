package com.prgm.aroundthetown.common.exception;

import com.prgm.aroundthetown.common.response.ApiResponse;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    // Todo : Add Valid Exception

    @ExceptionHandler
    private ResponseEntity<ApiResponse<String>> exceptionHandle(Exception exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
    }

    @ExceptionHandler(javassist.NotFoundException.class)
    private ResponseEntity<ApiResponse<String>> notFoundHandle(NotFoundException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> httpMessageNotReadableHandle(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> illegalArgumentHandle(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
}
