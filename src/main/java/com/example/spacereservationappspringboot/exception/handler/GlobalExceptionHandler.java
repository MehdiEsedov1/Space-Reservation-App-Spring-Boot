package com.example.spacereservationappspringboot.exception.handler;

import com.example.spacereservationappspringboot.dto.response.ExceptionResponseDTO;
import com.example.spacereservationappspringboot.exception.model.DatetimeParseException;
import com.example.spacereservationappspringboot.exception.model.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.exception.model.NotFoundException;
import com.example.spacereservationappspringboot.exception.model.WorkspaceSaveFailedException;
import com.example.spacereservationappspringboot.exception.type.ExceptionType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleNotFound(NotFoundException exception) {
        return buildResponse(exception.getExceptionType().getStatus(), exception.getMessage());
    }

    @ExceptionHandler(DatetimeParseException.class)
    public ResponseEntity<ExceptionResponseDTO> handleParce(DatetimeParseException exception) {
        return buildResponse(exception.getExceptionType().getStatus(), exception.getMessage());
    }

    @ExceptionHandler(WorkspaceSaveFailedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleSave(WorkspaceSaveFailedException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(InvalidTimeIntervalException.class)
    public ResponseEntity<ExceptionResponseDTO> handleInterval(InvalidTimeIntervalException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleOther(Exception exception) {
        return buildResponse(ExceptionType.INTERNAL_SERVER_ERROR.getStatus(), exception.getMessage());
    }

    private ResponseEntity<ExceptionResponseDTO> buildResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new ExceptionResponseDTO(
                        status,
                        message,
                        LocalDateTime.now()
                ),
                status
        );
    }
}