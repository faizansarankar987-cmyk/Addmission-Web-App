package com.isees.dtos;

import lombok.Data;

@Data
public class ExpensesDto {

    private String title;
    private String category;
    private Long amount;
    private String paidby;

    // keep string – frontend friendly
    private String date;

    // 🔥 FIXED (lowercase!)
    private String description;
    private String billurl;    
}
