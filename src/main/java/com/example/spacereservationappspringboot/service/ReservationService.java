package com.example.spacereservationappspringboot.service;

import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import com.example.spacereservationappspringboot.exception.NotFoundException;
import com.example.spacereservationappspringboot.mapper.ReservationMapper;
import com.example.spacereservationappspringboot.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final WorkspaceService workspaceService;
    private final ReservationMapper reservationMapper;

    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    public Optional<ReservationResponseDTO> makeReservation(String name, int workspaceId, Interval interval) {
        Optional<Workspace> workspaceOpt = tryGetWorkspace(workspaceId);
        if (workspaceOpt.isEmpty()) return Optional.empty();

        Workspace workspace = workspaceOpt.get();

        if (!workspaceService.isAvailable(workspaceId, interval)) {
            return Optional.empty();
        }

        Reservation reservation = new Reservation(name, workspace, interval);
        Reservation saved = reservationRepository.save(reservation);

        return Optional.of(reservationMapper.toDTO(saved));
    }

    public boolean cancelReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            return false;
        }
        reservationRepository.deleteById(id);
        return true;
    }

    private Optional<Workspace> tryGetWorkspace(int workspaceId) {
        try {
            return Optional.of(workspaceService.getWorkspaceById(workspaceId));
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }
}
