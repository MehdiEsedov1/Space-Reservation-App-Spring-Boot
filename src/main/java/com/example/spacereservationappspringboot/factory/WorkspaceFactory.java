package com.example.spacereservationappspringboot.factory;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceFactory {
    public Workspace createWorkspace(WorkspaceRequestDTO dto) {
        return new Workspace(dto.getName(), dto.getType(), dto.getPrice());
    }
}