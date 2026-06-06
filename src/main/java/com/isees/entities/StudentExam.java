package com.isees.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class StudentExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String email;

    private Long questionId;
    private String question;
    private String answer;
    private String correctAnswer;

    private int score;           // ✅ New result column
    private int totalQuestions;  // ✅ Total questions
    private String status;       // ✅ Passed / Failed (optional)
    
    private Double per;

    private LocalDateTime submittedAt = LocalDateTime.now();
}
