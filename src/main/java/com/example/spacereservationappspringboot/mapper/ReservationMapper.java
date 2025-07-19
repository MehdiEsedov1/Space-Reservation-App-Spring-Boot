package com.example.spacereservationappspringboot.mapper;

import com.example.spacereservationappspringboot.dto.response.ReservationResponseDTO;
import com.example.spacereservationappspringboot.entity.Reservation;
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
}
