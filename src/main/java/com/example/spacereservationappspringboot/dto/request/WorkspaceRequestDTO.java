package com.example.spacereservationappspringboot.dto.request;

import com.example.spacereservationappspringboot.enums.WorkspaceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkspaceRequestDTO {
    @NotBlank(message = "Workspace name is required.")
    private String name;

    @NotNull(message = "Workspace type is required.")
    private WorkspaceType type;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero.")
    private BigDecimal price;
}
