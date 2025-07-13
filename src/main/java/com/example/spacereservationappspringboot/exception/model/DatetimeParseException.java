package com.example.spacereservationappspringboot.exception.model;

import com.example.spacereservationappspringboot.exception.type.ExceptionType;

public class DatetimeParseException extends RuntimeException {
    private final ExceptionType exceptionType;

    public DatetimeParseException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
