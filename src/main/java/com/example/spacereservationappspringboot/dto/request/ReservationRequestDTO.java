package com.example.spacereservationappspringboot.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationRequestDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @Min(value = 1, message = "Workspace ID must be greater than 0.")
    private int workspaceId;

    @NotNull(message = "Start time is required.")
    @Future(message = "Start time must be in the future.")
    private Date startTime;

    @NotNull(message = "End time is required.")
    @Future(message = "End time must be in the future.")
    private Date endTime;
}
