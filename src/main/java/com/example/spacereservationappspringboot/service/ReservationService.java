package com.example.spacereservationappspringboot.service;

import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import com.example.spacereservationappspringboot.exception.model.NotFoundException;
import com.example.spacereservationappspringboot.exception.type.ExceptionType;
import com.example.spacereservationappspringboot.mapper.ReservationMapper;
import com.example.spacereservationappspringboot.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void makeReservation(String name, Long workspaceId, Interval interval) {
        Workspace workspace = tryGetWorkspace(workspaceId);

        if (!workspaceService.isAvailable(workspaceId, interval)) {
            return;
        }

        Reservation reservation = new Reservation(name, workspace, interval);
        Reservation saved = reservationRepository.save(reservation);

        reservationMapper.toDTO(saved);
    }

    public void cancelReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            return;
        }
        reservationRepository.deleteById(id);
    }

    private Workspace tryGetWorkspace(Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspaceById(workspaceId);
        if(workspace == null){
            throw new NotFoundException(ExceptionType.RESERVATION_NOT_FOUND);
        }
        return workspace;
    }
}
