package com.isees.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isees.dtos.Dto1;
import com.isees.entities.Entity1;
import com.isees.repos.Repo1;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class Service1 {

    @Autowired
    private Repo1 r1;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder encoder;

    private final Random random = new Random();

    // ✅ Signup
    public String signup(Dto1 d1) {
        Optional<Entity1> exists = r1.findByEmail(d1.getEmail());
        if (exists.isPresent()) return "Email already exists!";
        if (d1.getEmail() == null || d1.getEmail().isBlank()) return "Email is empty!";
        if (d1.getPassword() == null || d1.getPassword().isBlank()) return "Password is empty!";

        Entity1 e1 = new Entity1();
        e1.setEmail(d1.getEmail());
        e1.setPassword(encoder.encode(d1.getPassword()));
        r1.save(e1);
        return "Signup successfully!";
    }

    // ✅ Login
    public ResponseEntity<String> login(Dto1 d1) {
        Optional<Entity1> find = r1.findByEmail(d1.getEmail());

        if (find.isEmpty()) {
            return ResponseEntity.status(404).body("Wrong email id!");
        }

        Entity1 e1 = find.get();

        if (encoder.matches(d1.getPassword(), e1.getPassword())) {
            return ResponseEntity.ok("Login successfully!");
        } else {
            return ResponseEntity.status(401).body("Wrong password!");
        }
    }



    // ✅ Forget Password → Generate OTP & Send Email
    public String forgetpass(Dto1 d1) {
        Optional<Entity1> find = r1.findByEmail(d1.getEmail());
        if (find.isEmpty()) return "No user found with this email!";

        Entity1 user = find.get();

        // Generate 6-digit OTP
        String otp = String.valueOf(100000 + random.nextInt(900000));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        user.setOtp(otp);
        user.setOtpExpiry(expiry);
        r1.save(user);

        // ✅ Send OTP via email
        sendOtpEmail(user.getEmail(), otp);

        return "OTP sent to your registered email.";
    }

    // ✅ Reset Password → Verify OTP
    public String resetPassword(Dto1 d1) {
        Optional<Entity1> find = r1.findByEmail(d1.getEmail());
        if (find.isEmpty()) return "User not found!";

        Entity1 user = find.get();

        if (user.getOtp() == null || !user.getOtp().equals(d1.getOtp()))
            return "Invalid OTP!";

        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now()))
            return "OTP expired!";

        user.setPassword(encoder.encode(d1.getPassword()));
        user.setOtp(null);
        user.setOtpExpiry(null);
        r1.save(user);

        return "Password reset successful!";
    }

    // ✅ Send OTP Email
    public void sendOtpEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("ISEES Portal - Password Reset OTP");
            message.setText("Your OTP for password reset is: " + otp +
                    "\n\nThis OTP will expire in 5 minutes." +
                    "\n\nIf you didn’t request this, please ignore this email." +
                    "\n\nRegards,\nISEES Portal Team");

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("❌ Failed to send email: " + e.getMessage());
        }
    }
}
