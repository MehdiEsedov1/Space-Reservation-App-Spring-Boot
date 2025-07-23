package com.example.spacereservationappspringboot.factory;

import com.example.spacereservationappspringboot.entity.Interval;
import com.example.spacereservationappspringboot.entity.Reservation;
import com.example.spacereservationappspringboot.entity.Workspace;
import org.springframework.stereotype.Component;

@Component
public class ReservationFactory {
    public Reservation createReservation(String name, Workspace workspace, Interval interval) {
        return new Reservation(name, workspace, interval);
    }
}