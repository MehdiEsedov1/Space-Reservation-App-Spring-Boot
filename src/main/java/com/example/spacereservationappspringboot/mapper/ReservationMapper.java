package com.example.spacereservationappspringboot.mapper;

import com.example.spacereservationappspringboot.dto.request.ReservationRequestDTO;
import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponseDTO toDTO(Reservation reservation) {
        return ReservationResponseDTO.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .workspaceName(reservation.getWorkspace().getName())
                .startTime(reservation.getInterval().getStartTime())
                .endTime(reservation.getInterval().getEndTime())
                .build();
    }

    public Reservation toEntity(ReservationRequestDTO dto, Workspace workspace) {
        Interval interval = new Interval(dto.getStartTime(), dto.getEndTime());
        return new Reservation(dto.getName(), workspace, interval);
    }
}
