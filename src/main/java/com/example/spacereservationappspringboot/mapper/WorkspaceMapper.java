package com.example.spacereservationappspringboot.mapper;

import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMapper {
    public WorkspaceResponseDTO toDTO(Workspace workspace) {
        return WorkspaceResponseDTO.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .type(workspace.getType())
                .price(workspace.getPrice())
                .build();
    }
}
