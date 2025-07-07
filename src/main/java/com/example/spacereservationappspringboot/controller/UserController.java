package com.example.spacereservationappspringboot.controller;

import com.example.spacereservationappspringboot.dto.request.ReservationRequestDTO;
import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.exception.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.service.ReservationService;
import com.example.spacereservationappspringboot.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/reservations")
@RequiredArgsConstructor
public class UserController {

    private final WorkspaceService workspaceService;
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> viewReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @PostMapping
    public ResponseEntity<?> makeReservation(@RequestBody @Validated ReservationRequestDTO requestDTO) {
        Interval interval;
        try {
            interval = Interval.of(requestDTO.getStartTime(), requestDTO.getEndTime());
        } catch (InvalidTimeIntervalException e) {
            return ResponseEntity.badRequest().body("Invalid time interval: " + e.getMessage());
        }

        if (interval.getStartTime().before(new Date())) {
            return ResponseEntity.badRequest().body("Reservation time cannot be in the past.");
        }

        boolean available = workspaceService.isAvailable(requestDTO.getWorkspaceId(), interval);
        if (!available) {
            return ResponseEntity.badRequest().body("Workspace is not available for the selected interval.");
        }

        return reservationService
                .makeReservation(requestDTO.getName(), requestDTO.getWorkspaceId(), interval)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(@PathVariable("id") int id) {
        boolean cancelled = reservationService.cancelReservation(id);
        if (cancelled) {
            return ResponseEntity.ok("Reservation cancelled successfully.");
        } else {
            return ResponseEntity.status(404).body("Reservation not found.");
        }
    }
}
