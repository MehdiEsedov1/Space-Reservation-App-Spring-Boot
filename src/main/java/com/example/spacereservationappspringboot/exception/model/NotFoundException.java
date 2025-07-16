package com.example.spacereservationappspringboot.exception.model;

import com.example.spacereservationappspringboot.exception.type.ExceptionType;

public class NotFoundException extends RuntimeException {
    private final ExceptionType exceptionType;

    public NotFoundException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
