package com.isees.models;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

import com.isees.entities.CertificateEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class CertificatePdfGenerator {

    public static byte[] generate(CertificateEntity cert) {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 60, 60);
            PdfWriter writer = PdfWriter.getInstance(document, out);

            document.open();

            // ================= COLORS =================
            BaseColor darkBlue = new BaseColor(0, 51, 102);
            BaseColor gold = new BaseColor(200, 160, 60);
            BaseColor lightGray = new BaseColor(90, 90, 90);

            // ================= BORDER =================
            PdfContentByte canvas = writer.getDirectContent();

            Rectangle outer = new Rectangle(25, 25, PageSize.A4.getWidth() - 25, PageSize.A4.getHeight() - 25);
            outer.setBorder(Rectangle.BOX);
            outer.setBorderWidth(6);
            outer.setBorderColor(gold);
            canvas.rectangle(outer);

            Rectangle inner = new Rectangle(40, 40, PageSize.A4.getWidth() - 40, PageSize.A4.getHeight() - 40);
            inner.setBorder(Rectangle.BOX);
            inner.setBorderWidth(2);
            inner.setBorderColor(darkBlue);
            canvas.rectangle(inner);

            canvas.stroke();

            // ================= WATERMARK =================
            PdfContentByte watermark = writer.getDirectContentUnder();
            Font watermarkFont = new Font(Font.FontFamily.HELVETICA, 80, Font.BOLD, new BaseColor(230, 230, 230));

            Phrase watermarkText = new Phrase("ISEES", watermarkFont);
            ColumnText.showTextAligned(
                    watermark,
                    Element.ALIGN_CENTER,
                    watermarkText,
                    PageSize.A4.getWidth() / 2,
                    PageSize.A4.getHeight() / 2,
                    45
            );

            // ================= LOGO =================
            try {
                Image logo = Image.getInstance(
                        CertificatePdfGenerator.class.getClassLoader().getResource("static/logo.png")
                );
                logo.scaleToFit(90, 90);
                logo.setAlignment(Image.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception ignored) {}

            document.add(Chunk.NEWLINE);

            // ================= FONTS =================
            Font instFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, darkBlue);
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, gold);

            Font normalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
            Font smallFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, lightGray);

            Font nameFont = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.BOLD, darkBlue);
            Font courseFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, gold);

            // ================= HEADER =================
            Paragraph inst = new Paragraph("ISEES INSTITUTE", instFont);
            inst.setAlignment(Element.ALIGN_CENTER);
            inst.setSpacingAfter(5);
            document.add(inst);

            Paragraph sub = new Paragraph("Computer Education & Skill Development Center", smallFont);
            sub.setAlignment(Element.ALIGN_CENTER);
            sub.setSpacingAfter(20);
            document.add(sub);

            Paragraph title = new Paragraph("CERTIFICATE OF COMPLETION", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            // ================= CERT DETAILS =================
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(90);
            detailsTable.setWidths(new float[]{1, 1});

            PdfPCell left = new PdfPCell(new Phrase("Certificate No: " + safe(cert.getCertificateNo()), smallFont));
            left.setBorder(Rectangle.NO_BORDER);
            left.setHorizontalAlignment(Element.ALIGN_LEFT);

            String issueDate = cert.getIssuedate() != null
                    ? cert.getIssuedate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                    : "";

            PdfPCell right = new PdfPCell(new Phrase("Issue Date: " + issueDate, smallFont));
            right.setBorder(Rectangle.NO_BORDER);
            right.setHorizontalAlignment(Element.ALIGN_RIGHT);

            detailsTable.addCell(left);
            detailsTable.addCell(right);

            detailsTable.setSpacingAfter(40);
            document.add(detailsTable);

            // ================= BODY =================
            Paragraph p1 = new Paragraph("This is to certify that", normalFont);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setSpacingAfter(15);
            document.add(p1);

            Paragraph student = new Paragraph(safe(cert.getStudentName()).toUpperCase(), nameFont);
            student.setAlignment(Element.ALIGN_CENTER);
            student.setSpacingAfter(10);
            document.add(student);

            Paragraph email = new Paragraph("Email: " + safe(cert.getEmail()), normalFont);
            email.setAlignment(Element.ALIGN_CENTER);
            email.setSpacingAfter(30);
            document.add(email);

            Paragraph p2 = new Paragraph("has successfully completed the course:", normalFont);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setSpacingAfter(15);
            document.add(p2);

            Paragraph course = new Paragraph("\"" + safe(cert.getCourseName()).toUpperCase() + "\"", courseFont);
            course.setAlignment(Element.ALIGN_CENTER);
            course.setSpacingAfter(25);
            document.add(course);

            Paragraph p3 = new Paragraph(
                    "conducted by ISEES Institute with dedication and successful performance.",
                    normalFont
            );
            p3.setAlignment(Element.ALIGN_CENTER);
            p3.setSpacingAfter(45);
            document.add(p3);

            // ================= QR CODE =================
            String qrData =
                    "ISEES CERTIFICATE\n" +
                            "Student: " + safe(cert.getStudentName()) + "\n" +
                            "Email: " + safe(cert.getEmail()) + "\n" +
                            "Course: " + safe(cert.getCourseName()) + "\n" +
                            "Certificate No: " + safe(cert.getCertificateNo()) + "\n" +
                            "Issue Date: " + issueDate;

            BarcodeQRCode qrCode = new BarcodeQRCode(qrData, 120, 120, null);
            Image qrImage = qrCode.getImage();
            qrImage.setAlignment(Image.ALIGN_CENTER);
            document.add(qrImage);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            // ================= SIGNATURE + SEAL =================
            PdfPTable signTable = new PdfPTable(2);
            signTable.setWidthPercentage(90);
            signTable.setWidths(new float[]{1, 1});

            PdfPCell seal = new PdfPCell(new Phrase("INSTITUTE SEAL", smallFont));
            seal.setBorder(Rectangle.NO_BORDER);
            seal.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell sign = new PdfPCell(new Phrase("AUTHORIZED SIGNATORY", smallFont));
            sign.setBorder(Rectangle.NO_BORDER);
            sign.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell sealLine = new PdfPCell(new Phrase("__________________", normalFont));
            sealLine.setBorder(Rectangle.NO_BORDER);
            sealLine.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell signLine = new PdfPCell(new Phrase("__________________________", normalFont));
            signLine.setBorder(Rectangle.NO_BORDER);
            signLine.setHorizontalAlignment(Element.ALIGN_RIGHT);

            signTable.addCell(sealLine);
            signTable.addCell(signLine);

            signTable.addCell(seal);
            signTable.addCell(sign);

            document.add(signTable);

            // ================= FOOTER =================
            document.add(Chunk.NEWLINE);

            Paragraph footer = new Paragraph("ISEES INSTITUTE • Mahad, Raigad • Maharashtra", smallFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(20);
            document.add(footer);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private static String safe(String v) {
        return v == null ? "" : v;
    }
}