package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.exception.model.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.exception.model.WorkspaceSaveFailedException;
import com.example.spacereservationappspringboot.service.ReservationService;
import com.example.spacereservationappspringboot.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    @GetMapping("/workspaces")
    public List<WorkspaceResponseDTO> getAllWorkspaces() {
        return workspaceService.getAllWorkspaces();
    }

    @GetMapping("/workspaces/available")
    public List<WorkspaceResponseDTO> findAvailableWorkspaces(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date end) throws InvalidTimeIntervalException {
        Interval interval;
        interval = Interval.of(start, end);

        return workspaceService.getAvailableWorkspaces(interval);
    }

    @PostMapping("/workspaces")
    public void createWorkspace(@RequestBody @Valid WorkspaceRequestDTO requestDTO) {
        workspaceService.createWorkspace(requestDTO);
    }

    @PutMapping("/workspaces/{id}")
    public void updateWorkspace(@PathVariable Long id,
                                @RequestBody @Valid WorkspaceRequestDTO requestDTO) throws WorkspaceSaveFailedException {
        workspaceService.editWorkspace(id, requestDTO);
    }

    @DeleteMapping("/workspaces/{id}")
    public void deleteWorkspace(@PathVariable Long id) {
        workspaceService.deleteWorkspace(id);
    }

    @GetMapping("/reservations")
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/reservations/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}
