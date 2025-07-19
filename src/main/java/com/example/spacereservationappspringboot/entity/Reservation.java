package com.example.spacereservationappspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @Embedded
    private Interval interval;

    public Reservation(String name, Workspace workspace, Interval interval) {
        this.name = name;
        this.workspace = workspace;
        this.interval = interval;
    }
}
