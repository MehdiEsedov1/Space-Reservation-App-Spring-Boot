package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.exception.WorkspaceIsReservedException;
import com.example.spacereservationappspringboot.exception.WorkspaceSaveFailed;
import com.example.spacereservationappspringboot.service.ReservationService;
import com.example.spacereservationappspringboot.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    @GetMapping("/workspaces")
    public ResponseEntity<List<WorkspaceResponseDTO>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces());
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping("/workspaces")
    public ResponseEntity<?> createWorkspace(@RequestBody @Valid WorkspaceRequestDTO requestDTO) {
        try {
            WorkspaceResponseDTO created = workspaceService.createWorkspace(requestDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create workspace.");
        }
    }

    @PutMapping("/workspaces/{id}")
    public ResponseEntity<?> updateWorkspace(@PathVariable int id,
                                             @RequestBody @Valid WorkspaceRequestDTO requestDTO) {
        try {
            WorkspaceResponseDTO updated = workspaceService.editWorkspace(id, requestDTO);
            return ResponseEntity.ok(updated);
        } catch (WorkspaceSaveFailed e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/workspaces/{id}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable int id) {
        try {
            boolean deleted = workspaceService.deleteWorkspace(id);
            if (deleted) {
                return ResponseEntity.ok("Workspace deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Workspace not found.");
            }
        } catch (WorkspaceIsReservedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
