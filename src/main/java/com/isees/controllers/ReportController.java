package com.isees.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isees.services.ReportService;

@RestController
@RequestMapping("/api/report")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/data")
    public List<?> getReportData(
            @RequestParam String reportType,
            @RequestParam String from,
            @RequestParam String to
    ) {

        LocalDateTime fromDate = LocalDate.parse(from).atStartOfDay();
        LocalDateTime toDate = LocalDate.parse(to).atTime(23, 59, 59);

        if (reportType.equalsIgnoreCase("ADMISSION")) {
            return reportService.getAdmissionReport(fromDate, toDate);
        }

        if (reportType.equalsIgnoreCase("FEE")) {
            return reportService.getFeeReport(fromDate, toDate);
        }

        if (reportType.equalsIgnoreCase("EXPENSE")) {
            return reportService.getExpenseReport(fromDate, toDate);
        }

        throw new RuntimeException("Invalid report type");
    }
}