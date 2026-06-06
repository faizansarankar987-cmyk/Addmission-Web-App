package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isees.entities.Exam;
import com.isees.entities.ExamStatus;
import com.isees.entities.Questiontable;
import com.isees.repos.ExamRepository;
import com.isees.repos.QuestionRepository;
import com.isees.services.ExamService;

@RestController
@RequestMapping("/exam")
@CrossOrigin("*")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepo;

    // Create Exam
    @PostMapping("/create")
    public ResponseEntity<?> createExam(@RequestBody Exam exam) {

        if (exam.getExamTitle() == null || exam.getExamTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Title required");
        }

        if (exam.getCourseName() == null || exam.getCourseName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Course name required");
        }

        if (exam.getDuration() <= 0) {
            return ResponseEntity.badRequest().body("Duration must be > 0");
        }

        // default status if null
        if (exam.getStatus() == null) {
            exam.setStatus(ExamStatus.DRAFT);
        }

        return ResponseEntity.ok(examService.createExam(exam));
    }

    // Get all exams
    @GetMapping("/all")
    public List<Exam> getAllExams() {
        return examService.getAll();
    }

    // Get only active exams (student use)
    @GetMapping("/active")
    public List<Exam> getActiveExams() {
        return examRepository.findByStatus(ExamStatus.ACTIVE);
    }

    // Get all questions of an exam
    @GetMapping("/{examId}/questions")
    public List<Questiontable> getQuestions(@PathVariable Long examId) {
        return questionRepo.findByExam_Id(examId);
    }

    // Delete exam
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    // Activate exam
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateExam(@PathVariable Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        exam.setStatus(ExamStatus.ACTIVE);
        examRepository.save(exam);

        return ResponseEntity.ok("Exam Activated");
    }

    // Disable exam
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableExam(@PathVariable Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        exam.setStatus(ExamStatus.DISABLED);
        examRepository.save(exam);

        return ResponseEntity.ok("Exam Disabled");
    }
}