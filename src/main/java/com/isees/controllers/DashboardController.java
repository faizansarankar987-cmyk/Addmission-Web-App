package com.isees.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isees.entities.Application;
import com.isees.services.DashboardService;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{email}")
    public DashboardResponse getDashboard(@PathVariable String email) {
        List<Application> apps = dashboardService.getApplicationsByEmail(email);

        DashboardResponse response = new DashboardResponse();
        response.setStudentname(email);

        if (apps == null || apps.isEmpty()) {
            response.setApplications(List.of());
            return response;
        }

        // Student name from first record
        if (apps.get(0).getStudentname() != null && !apps.get(0).getStudentname().isEmpty()) {
            response.setStudentname(apps.get(0).getStudentname());
        }

        // Map applications safely
        response.setApplications(
            apps.stream().map(app -> {
                double total = app.getTotalFees() == null ? 0.0 : app.getTotalFees();
                double paid = app.getAmountPaid() == null ? 0.0 : app.getAmountPaid();
                double remaining = total - paid;
                if (remaining < 0) remaining = 0.0;

                String payStatus = (paid == 0) ? "UNPAID" : (remaining == 0 ? "PAID" : "PARTIAL");

                return new ApplicationSummary(
                    app.getId(),
                    app.getCoursename(),
                    app.getApplicationStatus() == null ? "PENDING" : app.getApplicationStatus(),
                    payStatus,
                    total,
                    paid,
                    remaining
                );
            }).collect(Collectors.toList())
        );

        return response;
    }

    // ================= DTO Classes =================
    public static class DashboardResponse {
        private String studentname;
        private List<ApplicationSummary> applications;

        public String getStudentname() { return studentname; }
        public void setStudentname(String studentname) { this.studentname = studentname; }
        public List<ApplicationSummary> getApplications() { return applications; }
        public void setApplications(List<ApplicationSummary> applications) { this.applications = applications; }
    }

    public static class ApplicationSummary {
        private Long id;
        private String coursename;
        private String applicationStatus;
        private String paymentStatus;
        private double totalFees;
        private double amountPaid;
        private double remainingFees;

        public ApplicationSummary(Long id, String coursename, String applicationStatus,
                                  String paymentStatus, double totalFees, double amountPaid, double remainingFees) {
            this.id = id;
            this.coursename = coursename;
            this.applicationStatus = applicationStatus;
            this.paymentStatus = paymentStatus;
            this.totalFees = totalFees;
            this.amountPaid = amountPaid;
            this.remainingFees = remainingFees;
        }

        public Long getId() { return id; }
        public String getCoursename() { return coursename; }
        public String getApplicationStatus() { return applicationStatus; }
        public String getPaymentStatus() { return paymentStatus; }
        public double getTotalFees() { return totalFees; }
        public double getAmountPaid() { return amountPaid; }
        public double getRemainingFees() { return remainingFees; }
    }
}
