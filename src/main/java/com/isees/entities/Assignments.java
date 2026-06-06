package com.isees.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Assignments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private String coursename;      // which course
    private String subject;         // subject name

    private String title;           // assignment title
    private String description;     // details/instructions

    private String fileUrl;         // PDF / DOC link
//    private String uploadDate;      // auto set when added

    private String dueDate;         // last submission date
    
    private String email;
}
