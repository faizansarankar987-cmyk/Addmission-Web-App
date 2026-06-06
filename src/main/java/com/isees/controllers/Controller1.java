package com.isees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isees.dtos.Dto1;
import com.isees.repos.Repo1;
import com.isees.services.Service1;

@RestController
@RequestMapping("/student")
@CrossOrigin("http://localhost:5173")
public class Controller1 {

    @Autowired
    private Service1 s1;
    @Autowired Repo1 repo;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Dto1 d1) {
        return s1.login(d1);  // return service response directly
    }
    @GetMapping("/count")
    public long countStudents() {
        return repo.count();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Dto1 d1) {
        return ResponseEntity.ok(s1.signup(d1));
    }

    // 🔹 Step 1: Forget Password → generate OTP
    @PostMapping("/forget")
    public ResponseEntity<String> forget(@RequestBody Dto1 d1) {
        String msg = s1.forgetpass(d1);
        return ResponseEntity.ok(msg);
    }

    // 🔹 Step 2: Reset Password → verify OTP & set new password
    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestBody Dto1 d1) {
        String msg = s1.resetPassword(d1);
        return ResponseEntity.ok(msg);
    }
}
