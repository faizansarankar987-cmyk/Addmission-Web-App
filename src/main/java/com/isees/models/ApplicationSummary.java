package com.isees.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSummary {
    private Long id;
    private String coursename;
    private String applicationStatus;
    private String paymentStatus;
    private Double totalFees;
    private Double amountPaid;
    private Double remainingFees;
}
