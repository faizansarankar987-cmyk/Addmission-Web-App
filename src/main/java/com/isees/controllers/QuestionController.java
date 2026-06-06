package com.isees.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isees.entities.Questiontable;
import com.isees.services.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Add multiple questions for a specific exam
    @PostMapping("/add/{examId}")
    public ResponseEntity<String> addQuestions(
            @PathVariable Long examId,
            @RequestBody List<Questiontable> questions) {

        String msg = questionService.addQuestions(examId, questions);
        return ResponseEntity.ok(msg);
    }

    // Fetch all questions for one exam
    @GetMapping("/exam/{examId}")
    public List<Questiontable> getQuestions(@PathVariable Long examId) {
        return questionService.getQuestionsByExam(examId);
    }

    // Update ONE question
    @PutMapping("/update/{questionId}")
    public ResponseEntity<String> updateQuestion(
            @PathVariable Long questionId,
            @RequestBody Questiontable updatedQ) {

        String msg = questionService.updateQuestion(questionId, updatedQ);
        return ResponseEntity.ok(msg);
    }
    @PutMapping("/update/exam/{examId}")
    public ResponseEntity<String> updateAllQuestions(
            @PathVariable Long examId,
            @RequestBody List<Questiontable> newQuestions) {

        String msg = questionService.updateAllQuestions(examId, newQuestions);
        return ResponseEntity.ok(msg);
    }
    @PostMapping("/add1/{examId}")
    public ResponseEntity<String> addQuestion(
            @PathVariable Long examId,
            @RequestBody Questiontable q) {

        String msg = questionService.addSingleQuestion(examId, q);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/count")
    public long countAllQuestions() {
        return questionService.countAll();
    }
 // Fetch single question by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<Questiontable> getOne(@PathVariable Long questionId) {
        Questiontable q = questionService.getOne(questionId);
        return ResponseEntity.ok(q);
    }


}
