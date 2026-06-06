package com.isees.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isees.entities.CertificateEntity;
import com.isees.repos.CertificateRepo;
import com.isees.services.ApplicationService;
import com.isees.services.CertificateSer;

@RestController
@RequestMapping("/certificate")
@CrossOrigin("*")
public class CertificateControl {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CertificateSer certificateSer;

    @Autowired
    private CertificateRepo certificateRepo;

    // =====================================
    // CHECK FEE STATUS
    // =====================================
    @GetMapping("/checkFee")
    public ResponseEntity<Map<String, Object>> checkFee(
            @RequestParam String email,
            @RequestParam String courseName) {

        Map<String, Object> resp = new HashMap<>();

        boolean feeComplete = applicationService.isFeeComplete(email, courseName);
        double remaining = applicationService.getRemainingAmount(email, courseName);

        if (feeComplete && remaining <= 0) {
            remaining = 0;
        }

        resp.put("feeComplete", feeComplete);
        resp.put("remaining", remaining);

        return ResponseEntity.ok(resp);
    }

    // =====================================
    // ADMIN CREATE CERTIFICATE
    // =====================================
//    @PostMapping("/create")
//    public ResponseEntity<?> createCertificate(@RequestBody Map<String, String> data) {
//
//        String email = data.get("email");
//        String studentName = data.get("studentName");
//        String courseName = data.get("courseName");
//
//        if (email == null || studentName == null || courseName == null) {
//            return ResponseEntity.badRequest().body("Missing data");
//        }
//
//        CertificateEntity cert = certificateSer.createCertificate(email, studentName, courseName);
//
//        return ResponseEntity.ok(Map.of(
//                "status", "SUCCESS",
//                "message", "Certificate created successfully",
//                "certificateNo", cert.getCertificateNo()
//        ));
//    }

    // =====================================
    // STUDENT CHECK CERTIFICATE STATUS
    // =====================================
//    @GetMapping("/status")
//    public ResponseEntity<?> status(@RequestParam String email,
//                                    @RequestParam String courseName) {
//
//        boolean available = certificateRepo.existsByEmailAndCourseName(email, courseName);
//
//        return ResponseEntity.ok(Map.of("available", available));
//    }

    // =====================================
    // STUDENT DOWNLOAD CERTIFICATE
    // =====================================
//    @GetMapping("/download")
//    public ResponseEntity<byte[]> download(@RequestParam String email,
//                                           @RequestParam String courseName) {
//
//        boolean available = certificateRepo.existsByEmailAndCourseName(email, courseName);
//
//        if (!available) {
//            return ResponseEntity.status(403).body(null);
//        }
//
//        byte[] pdfBytes = certificateSer.downloadCertificate(email, courseName);
//
//        if (pdfBytes == null || pdfBytes.length == 0) {
//            return ResponseEntity.status(500).body(null);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDisposition(
//                ContentDisposition.attachment()
//                        .filename("certificate.pdf")
//                        .build()
//        );
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }
   

        // STUDENT CHECK CERTIFICATE STATUS
        @GetMapping("/status")
        public ResponseEntity<?> status(@RequestParam String email,
                                        @RequestParam String courseName) {

            boolean available = certificateRepo.existsByEmailAndCourseName(email, courseName);

            return ResponseEntity.ok(Map.of("available", available));
        }

        // ADMIN CREATE CERTIFICATE
        @PostMapping("/create")
        public ResponseEntity<?> createCertificate(@RequestBody Map<String, String> data) {

            String email = data.get("email");
            String studentName = data.get("studentName");
            String courseName = data.get("courseName");

            if (email == null || studentName == null || courseName == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Missing data"));
            }

            CertificateEntity cert = certificateSer.createCertificate(email, studentName, courseName);

            return ResponseEntity.ok(Map.of(
                    "status", "SUCCESS",
                    "certificateNo", cert.getCertificateNo()
            ));
        }

        // STUDENT DOWNLOAD CERTIFICATE
        @GetMapping("/download")
        public ResponseEntity<byte[]> download(@RequestParam String email,
                                               @RequestParam String courseName) {

            byte[] pdfBytes = certificateSer.downloadCertificate(email, courseName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.attachment()
                    .filename("certificate.pdf")
                    .build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        }
        @GetMapping("/all")
        public ResponseEntity<?> getAllCertificates(){
            return ResponseEntity.ok(certificateRepo.findAll());
        }
    }
