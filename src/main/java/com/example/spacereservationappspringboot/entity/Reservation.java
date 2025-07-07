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
    private int id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Embedded
    private Interval interval;

    public Reservation(String name, Workspace workspace, Interval interval) {
        this.name = name;
        this.workspace = workspace;
        this.interval = interval;
    }
}
