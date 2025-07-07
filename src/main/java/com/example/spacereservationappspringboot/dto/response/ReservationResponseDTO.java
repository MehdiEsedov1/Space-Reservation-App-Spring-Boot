package com.example.spacereservationappspringboot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReservationResponseDTO {
    private int id;
    private String name;
    private String workspaceName;
    private Date startTime;
    private Date endTime;
}
