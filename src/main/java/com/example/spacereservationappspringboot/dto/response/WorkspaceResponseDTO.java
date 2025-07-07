package com.example.spacereservationappspringboot.dto.response;

import com.example.spacereservationappspringboot.enums.WorkspaceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WorkspaceResponseDTO {
    private int id;
    private String name;
    private WorkspaceType type;
    private BigDecimal price;
}
