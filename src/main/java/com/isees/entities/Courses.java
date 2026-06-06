package com.isees.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String coursename;
    private String duration;
//    private String eligibility;
//    private Long totalseats;
//    private Long availableseat;
//    private Long confirmseats;
    private Double fees;
}

