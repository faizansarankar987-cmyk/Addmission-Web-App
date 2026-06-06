package com.isees.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.CertificateEntity;
import com.isees.models.CertificatePdfGenerator;
import com.isees.repos.ApplicationRepo;
import com.isees.repos.CertificateRepo;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class CertificateSer {

    @Autowired
    private CertificateRepo cerRepo;

    @Autowired
    private ApplicationService feeSer;

    @Autowired
    private ApplicationRepo admissionRepo;

    public byte[] downloadCertificate(String email, String studentName, String courseName) {

        // ✅ Check student exists
        admissionRepo.findByEmailid(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // ✅ Check fee completion
        if (!feeSer.isFeeComplete(email, courseName)) {
            throw new RuntimeException("Course fee not completed");
        }


        // ✅ Fetch or create certificate entity
        CertificateEntity certificateEntity = cerRepo
                .findByEmailAndCourseName(email, courseName)
                .orElseGet(() -> createCertificateEntity(email, studentName, courseName));

        // ✅ Generate PDF directly from entity
        return CertificatePdfGenerator.generate(certificateEntity);
    }

    private CertificateEntity createCertificateEntity(String email, String studentName, String courseName) {

        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setEmail(email);
        certificateEntity.setStudentName(studentName);
        certificateEntity.setCourseName(courseName);
        certificateEntity.setCertificateNo("CERT-" + System.currentTimeMillis());
        certificateEntity.setIssuedate(LocalDate.now());

        return cerRepo.save(certificateEntity);
    }
    public CertificateEntity createCertificate(String email, String studentName, String courseName) {

        // already exists
        if (cerRepo.existsByEmailAndCourseName(email, courseName)) {
            throw new RuntimeException("Certificate already created!");
        }

        CertificateEntity cert = new CertificateEntity();
        cert.setEmail(email);
        cert.setStudentName(studentName);
        cert.setCourseName(courseName);
        cert.setIssuedate(LocalDate.now());

        // generate certificate no
        String certNo = "ISEES-" + System.currentTimeMillis();
        cert.setCertificateNo(certNo);

        return cerRepo.save(cert);
    }

    // ==========================================
    // STUDENT DOWNLOAD CERTIFICATE PDF
    // ==========================================
    public byte[] downloadCertificate(String email, String courseName) {

        CertificateEntity cert = cerRepo
                .findByEmailAndCourseName(email, courseName)
                .orElseThrow(() -> new RuntimeException("Certificate not found"));

        // ✅ MUST CALL NEW PROFESSIONAL GENERATOR
        return CertificatePdfGenerator.generate(cert);
    }

    // ==========================================
    // PDF GENERATION
    // ==========================================
    private byte[] generateCertificatePDF(CertificateEntity cert) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("ISEES INSTITUTE"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("CERTIFICATE OF COMPLETION"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Certificate No: " + cert.getCertificateNo()));
            document.add(new Paragraph("Issue Date: " + cert.getIssuedate()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("This is to certify that"));
            document.add(new Paragraph(cert.getStudentName()));
            document.add(new Paragraph("Email: " + cert.getEmail()));
            document.add(new Paragraph("has successfully completed the course:"));
            document.add(new Paragraph(cert.getCourseName()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Authorized Signatory"));
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed: " + e.getMessage());
        }
    }
}

