package com.prgm.aroundthetown.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
//TODO: 기존에 만들어 둔 에외와 같은 예외를 처리해서 에러를 발생시키고 있습니다. 설정을 통일해야할 것 같습니다.

//    @ExceptionHandler(Exception.class)
//    public ApiResponse<String> exceptionHandle(final Exception e) {
//        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
//    }

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Object> notFoundHandler(final NotFoundException e) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorHandler(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}