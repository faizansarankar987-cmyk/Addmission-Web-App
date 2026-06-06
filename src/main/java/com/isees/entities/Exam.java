package com.isees.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examTitle;
    private String courseName;
    private LocalDate examDate;
    private int duration;
    private int totalMarks;

   
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Questiontable> questions = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    
    @Column(nullable = false)
    private ExamStatus status = ExamStatus.DRAFT; // default value
}
