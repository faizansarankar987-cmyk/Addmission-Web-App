package com.isees.dtos;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ApplicationDto {

    private String studentname;
    private String coursename;
    private LocalDateTime date;

    private String fathername;
    private String mothername;

    private Double percentage10;
    private Double percentage12;

    private String mobilenumber;
    private String dateofbirth;
    private String address;

    private MultipartFile adharFile;
    private MultipartFile photoFile;
    private MultipartFile signatureFile;
    private MultipartFile marksheetFile;

    private String emailid;
    private String gender;

    private String state;
    private String district;
    private String taluka;

    private Double totalFees;
    private Double amountPaid = 0.0;
    private Double remainingFees = 0.0;

}
