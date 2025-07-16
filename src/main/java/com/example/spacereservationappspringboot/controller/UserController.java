package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.ReservationRequestDTO;
import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.dto.response.WorkspaceResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.exception.model.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.service.ReservationService;
import com.example.spacereservationappspringboot.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/reservations")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationResponseDTO> viewReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public void makeReservation(@RequestBody @Validated ReservationRequestDTO requestDTO) throws InvalidTimeIntervalException {
        Interval interval = Interval.of(requestDTO.getStartTime(), requestDTO.getEndTime());
        reservationService.makeReservation(requestDTO.getName(), requestDTO.getWorkspaceId(), interval);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }

    @GetMapping("/available")
    public List<WorkspaceResponseDTO> findAvailableWorkspaces(
            @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date start,
            @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date end) throws InvalidTimeIntervalException {
        Interval interval = Interval.of(start, end);
        return workspaceService.getAvailableWorkspaces(interval);
    }
}
