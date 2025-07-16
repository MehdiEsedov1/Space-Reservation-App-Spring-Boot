package com.example.spacereservationappspringboot.entity;

import com.example.spacereservationappspringboot.enums.WorkspaceType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private WorkspaceType type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public Workspace(String name, WorkspaceType type, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
