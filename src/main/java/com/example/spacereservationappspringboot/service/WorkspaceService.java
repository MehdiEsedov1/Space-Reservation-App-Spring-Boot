package com.example.spacereservationappspringboot.service;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import com.example.spacereservationappspringboot.exception.model.NotFoundException;
import com.example.spacereservationappspringboot.exception.type.ExceptionType;
import com.example.spacereservationappspringboot.factory.WorkspaceFactory;
import com.example.spacereservationappspringboot.mapper.WorkspaceMapper;
import com.example.spacereservationappspringboot.repository.ReservationRepository;
import com.example.spacereservationappspringboot.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final ReservationRepository reservationRepository;
    private final WorkspaceMapper workspaceMapper;
    private final WorkspaceFactory workspaceFactory;

    public Workspace getWorkspaceById(Long id) {
        return workspaceRepository.findById(id).orElseThrow();
    }

    public List<WorkspaceResponseDTO> getAllWorkspaces() {
        return workspaceRepository.findAll().stream()
                .map(workspaceMapper::toDTO)
                .toList();
    }

    public List<WorkspaceResponseDTO> getAvailableWorkspaces(Interval interval) {
        return workspaceRepository.findAll().stream()
                .filter(workspace -> isAvailable(workspace.getId(), interval))
                .map(workspaceMapper::toDTO)
                .toList();
    }

    public void createWorkspace(WorkspaceRequestDTO dto) {
        Workspace workspace = workspaceFactory.createWorkspace(dto);
        Workspace saved = workspaceRepository.save(workspace);
        workspaceMapper.toDTO(saved);
    }

    public void editWorkspace(Long id, WorkspaceRequestDTO dto) {
        if (!workspaceRepository.existsById(id)) {
            throw new NotFoundException(ExceptionType.WORKSPACE_NOT_FOUND);
        }

        Workspace workspace = workspaceFactory.createWorkspace(dto);
        workspace.setId(id);
        Workspace saved = workspaceRepository.save(workspace);
        workspaceMapper.toDTO(saved);
    }

    public void deleteWorkspace(Long id) {
        if (!workspaceRepository.existsById(id)) {
            return;
        }
        workspaceRepository.deleteById(id);
    }

    public boolean isAvailable(Long workspaceId, Interval interval) {
        List<Reservation> reservations = reservationRepository.findAllByWorkspaceId(workspaceId);
        return reservations.stream()
                .noneMatch(existing -> Interval.isOverlap(interval, existing.getInterval()));
    }
}
