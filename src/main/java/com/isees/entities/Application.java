package com.isees.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentname;
    private String fathername;
    private String mothername;
    private String gender;
    private String dateofbirth;
    private String mobilenumber;
    private String emailid;
    private String address;
    private String coursename;
//    private String percentage10;
//    private String percentage12;

    private Double totalFees;       // ✅ use Double, not double
    private Double amountPaid;      // ✅
    private Double remainingFees; 
    private String state;
    private String district;
    private String taluka;
    @Column(nullable = false)
    private String applicationStatus = "PENDING";

    @Column(nullable = false)
    private String paymentStatus = "UNPAID"; // UNPAID / PARTIAL / PAID

    private String adharcard_img;
    
    private String signature_img;
    private String photo_img;
    private String markshitphoto;
    
    private LocalDateTime date;

    // Helper method to update remaining and status
//    public void updatePayment(double paidAmount) {
//        this.amountPaid += paidAmount;
//        this.remainingFees = this.totalFees - this.amountPaid;
//        if (remainingFees <= 0) {
//            this.remainingFees = (double) 0;
//            this.paymentStatus = "PAID";
//        } else {
//            this.paymentStatus = "PARTIAL";
//        }
//    } 
    private Boolean enrolled = false;
    @ElementCollection
    private List<String> stripeSessionIds = new ArrayList<>();

	private String upiScreenshot;

    // Getters & Setters
    public List<String> getStripeSessionIds() {
        return stripeSessionIds;
    }

    public void setStripeSessionIds(List<String> stripeSessionIds) {
        this.stripeSessionIds = stripeSessionIds;
    }

   

    public String getUpiScreenshot() {
        return upiScreenshot;
    }

    public void setUpiScreenshot(String upiScreenshot) {
        this.upiScreenshot = upiScreenshot;
    }
    

    private LocalDateTime paymentDate;
    
    public void updatePayment(double paidAmount) {

        if(this.amountPaid == null) this.amountPaid = 0.0;

        this.amountPaid += paidAmount;
        this.remainingFees = this.totalFees - this.amountPaid;

        // ✅ VERY IMPORTANT
        this.paymentDate = LocalDateTime.now();

        if (remainingFees <= 0) {
            this.remainingFees = 0.0;
            this.paymentStatus = "PAID";
        } else {
            this.paymentStatus = "PARTIAL";
        }
    }
    // Update payment helper
  
}
