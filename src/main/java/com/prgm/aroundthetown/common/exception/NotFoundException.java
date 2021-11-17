package com.prgm.aroundthetown.common.exception;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(final String msg) {
        super(msg);
    }
}
