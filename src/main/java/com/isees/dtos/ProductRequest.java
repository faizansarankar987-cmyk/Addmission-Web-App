package com.isees.dtos;

import lombok.Data;

@Data
public class ProductRequest {
    private String email;
    private String studentname;
    private String coursename;
    private Double fees;         // amount user is paying now
    private Double totalFees;    // full course fees
    private String currency;
    private Long applicationId;  // <-- add this

    // Optional getter (Lombok @Data already generates one)
    // public Long getApplicationId() { return applicationId; }
}
