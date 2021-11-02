package com.prgm.aroundthetown.common.exception;

import com.prgm.aroundthetown.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> exceptionHandle(final Exception e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

}
