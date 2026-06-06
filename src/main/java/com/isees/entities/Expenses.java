package com.isees.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expensesid;

    private String title;
    private String category;
    private Long amount;
    private String paidby;

    private LocalDateTime date;

    private String description;
    private String billurl;
}