package com.isees.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Entity1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    // 🔹 For forget-password
    private String otp;

    // ✅ FIX: Use LocalDateTime instead of Long
    private LocalDateTime otpExpiry;
}
