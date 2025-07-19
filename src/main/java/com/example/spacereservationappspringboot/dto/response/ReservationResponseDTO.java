package com.example.spacereservationappspringboot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponseDTO {
    private Long id;
    private String name;
    private String workspaceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
