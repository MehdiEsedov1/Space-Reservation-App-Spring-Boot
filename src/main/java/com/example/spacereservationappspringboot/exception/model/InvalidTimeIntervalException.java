package com.example.spacereservationappspringboot.exception.model;

import com.example.spacereservationappspringboot.exception.type.ExceptionType;

public class InvalidTimeIntervalException extends RuntimeException {
    private final ExceptionType exceptionType;

    public InvalidTimeIntervalException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}

