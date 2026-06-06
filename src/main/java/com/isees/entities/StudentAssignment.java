package com.isees.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submissionId;

    // Foreign key to Assignments
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignments assignment;

    private String studentEmail;
    private String studentName;

    private String fileUrl;        // student's uploaded answer
    private String uploadDate;     // submission date
}
