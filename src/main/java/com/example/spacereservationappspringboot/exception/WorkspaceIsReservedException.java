package com.example.spacereservationappspringboot.exception;

public class WorkspaceIsReservedException extends RuntimeException {
    public WorkspaceIsReservedException(String message) {
        super(message);
    }
}
