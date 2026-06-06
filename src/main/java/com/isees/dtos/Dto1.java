package com.isees.dtos;

import lombok.Data;

@Data
public class Dto1 {
    private String email;
    private String password; // new password (for reset)
    private String otp;      // user entered OTP
}
