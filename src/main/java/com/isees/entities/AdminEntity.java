package com.isees.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AdminEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminid;
	private String adminusername;
	private String adminpass;
    private String otp;

    // ✅ FIX: Use LocalDateTime instead of Long
    private LocalDateTime otpExpiry;
	
	
}
