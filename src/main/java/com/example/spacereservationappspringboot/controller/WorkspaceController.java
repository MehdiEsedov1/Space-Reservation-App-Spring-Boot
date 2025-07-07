package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.WorkspaceRequestDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.exception.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping
    public ResponseEntity<List<WorkspaceResponseDTO>> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces());
    }

    @GetMapping("/available")
    public ResponseEntity<?> findAvailableWorkspaces(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date end) {

        Interval interval;
        try {
            interval = Interval.of(start, end);
        } catch (InvalidTimeIntervalException e) {
            return ResponseEntity.badRequest().body("Invalid time interval: " + e.getMessage());
        }

        List<WorkspaceResponseDTO> available = workspaceService.getAvailableWorkspaces(interval);
        return ResponseEntity.ok(available);
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponseDTO> createWorkspace(
            @RequestBody @Validated WorkspaceRequestDTO requestDTO) {
        WorkspaceResponseDTO responseDTO = workspaceService.createWorkspace(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
