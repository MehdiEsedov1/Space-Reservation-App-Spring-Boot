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

    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceType type;

    private BigDecimal price;

    public Workspace(String name, WorkspaceType type, BigDecimal price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }
}
