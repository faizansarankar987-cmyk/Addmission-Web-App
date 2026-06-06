package com.isees.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="massages")
public class MassagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String massage;

    private String type;

    private boolean readStatus = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}