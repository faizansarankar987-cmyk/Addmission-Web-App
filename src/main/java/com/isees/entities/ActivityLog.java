package com.isees.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="activity_logs")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;   // admin name/email

    private String action;     // "Added Student", "Deleted Course" etc

    private String module;     // "STUDENT", "COURSE", "EXAM", "FINANCE"

    private LocalDateTime createdAt = LocalDateTime.now();
}