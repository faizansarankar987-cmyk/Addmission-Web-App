package com.isees.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.ApplicationDto;
import com.isees.dtos.CoursePopularityDto;
import com.isees.entities.Application;
import com.isees.services.ApplicationService;

@RestController
@RequestMapping("/application")
@CrossOrigin("*")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    // =====================================================
    // APPLY ADMISSION
    // =====================================================
    @PostMapping("/apply")

    public ResponseEntity<Map<String, Object>> submitApplication(
            @ModelAttribute ApplicationDto dto,
            @RequestParam MultipartFile adharFile,
            @RequestParam MultipartFile photoFile,
            @RequestParam MultipartFile signatureFile,
            @RequestParam MultipartFile marksheetFile) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Save application and get numeric ID
            Long appId = service.saveApplication((ApplicationDto) dto, adharFile, photoFile, signatureFile, marksheetFile);

            response.put("status", "success");
            response.put("message", "Application submitted successfully");
            response.put("applicationId", appId);  // ✅ Return numeric appId for Stripe
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // =====================================================
    // GET BY EMAIL
    // =====================================================
    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<Application> getApplicationsByEmail(@PathVariable String email) {
        Application app = service.getByEmailSingle(email);
        if (app == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(app);
    }

    // =====================================================
    // GET BY ID
    // =====================================================
    @GetMapping("/getById/{id}")
    public ResponseEntity<Application> getById(@PathVariable Long id) {
        Optional<Application> app = service.getById(id);
        if (app.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(app.get());
    }

    // =====================================================
    // GET ALL
    // =====================================================
    @GetMapping("/getall")
    public List<Application> getAllApplications() {
        return service.getAll();
    }

    // =====================================================
    // STUDENT DASHBOARD
    // =====================================================
    @GetMapping("/dashboard/{email}")
    public ResponseEntity<?> getStudentDashboard(@PathVariable String email) {

        Application student = service.getByEmailSingle(email);

        if (student == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Student not found"));
        }

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("studentname", student.getStudentname());
        dashboard.put("emailid", student.getEmailid());
        dashboard.put("coursename", student.getCoursename());
        dashboard.put("totalfee", student.getTotalFees());
        dashboard.put("amountPaid", student.getAmountPaid());
        dashboard.put("remainingFees", student.getRemainingFees());
        dashboard.put("paymentStatus", student.getPaymentStatus());
        dashboard.put("applicationStatus", student.getApplicationStatus());
        dashboard.put("enrolled", student.getEnrolled());

        return ResponseEntity.ok(dashboard);
    }

    // =====================================================
    // GET BY EMAIL + COURSE (FOR PAY FEES PAGE)
    // =====================================================
    @GetMapping("/getByEmailCourse")
    public ResponseEntity<?> getByEmailAndCourse(
            @RequestParam String email,
            @RequestParam String course) {

        Application app = service.getByEmailAndCourse(email, course);

        if (app == null) {
            return ResponseEntity.ok(Map.of());
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("studentname", app.getStudentname());
        resp.put("emailid", app.getEmailid());
        resp.put("coursename", app.getCoursename());
        resp.put("fees", app.getTotalFees());
        resp.put("amountPaid", app.getAmountPaid());
        resp.put("remainingFees", app.getRemainingFees());
        resp.put("paymentStatus", app.getPaymentStatus());
        resp.put("enrolled", app.getEnrolled());

        return ResponseEntity.ok(resp);
    }

    // =====================================================
    // UPDATE PAYMENT & ENROLL STUDENT
    // =====================================================
    @PostMapping("/updatePayment")
    public ResponseEntity<String> updatePayment(
            @RequestBody Map<String, Object> data) {

        String email = (String) data.get("email");
        String course = (String) data.get("course");
        Double amount = Double.parseDouble(data.get("amount").toString());

        String msg = service.updatePayment(email, course, amount);

        return ResponseEntity.ok(msg);
    }
    @GetMapping("/recent")
    public List<Application> recent(){
        return service.getRecentAdmissions();
    }
    @GetMapping("/pending-fees")
    public ResponseEntity<Double> getPendingFees() {
        Double total = service.getPendingFees();
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
    @GetMapping("/monthly-income")
    public Double getMonthlyIncome(){
        return service.getMonthlyIncome();
    }
    
    @GetMapping("/course-popularity")
    public List<CoursePopularityDto> coursePopularity(){
        return service.getCoursePopularity();
    }
}
