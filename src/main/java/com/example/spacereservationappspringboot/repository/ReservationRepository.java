package com.example.spacereservationappspringboot.repository;

import com.example.spacereservationappspringboot.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByWorkspaceId(Long workspaceId);
}
