package com.example.spacereservationappspringboot.service;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import com.example.spacereservationappspringboot.exception.NotFoundException;
import com.example.spacereservationappspringboot.exception.WorkspaceSaveFailed;
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

    public Workspace getWorkspaceById(int id) throws NotFoundException {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found!"));
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

    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO dto) {
        Workspace workspace = workspaceMapper.toEntity(dto);
        Workspace saved = workspaceRepository.save(workspace);
        return workspaceMapper.toDTO(saved);
    }

    public WorkspaceResponseDTO editWorkspace(int id, WorkspaceRequestDTO dto) throws WorkspaceSaveFailed {
        if (!workspaceRepository.existsById(id)) {
            throw new WorkspaceSaveFailed("Couldn't save workspace: not found.");
        }

        Workspace workspace = workspaceMapper.toEntity(dto);
        workspace.setId(id);
        Workspace saved = workspaceRepository.save(workspace);
        return workspaceMapper.toDTO(saved);
    }

    public boolean deleteWorkspace(int id) {
        if (!workspaceRepository.existsById(id)) {
            return false;
        }
        workspaceRepository.deleteById(id);
        return true;
    }

    public boolean isAvailable(int workspaceId, Interval interval) {
        List<Reservation> reservations = reservationRepository.findAllByWorkspaceId(workspaceId);
        return reservations.stream()
                .noneMatch(existing -> Interval.isOverlap(interval, existing.getInterval()));
    }
}
