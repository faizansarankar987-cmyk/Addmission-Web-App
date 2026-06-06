package com.isees.dtos;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class AdminEntityDto {

	private String adminusername;
	private String adminpass;
    private String otp;

    // ✅ FIX: Use LocalDateTime instead of Long
    private LocalDateTime otpExpiry;
}
