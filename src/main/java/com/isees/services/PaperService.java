package com.isees.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.dtos.ExamResultDto;
import com.isees.dtos.PaperSubmitDto;
import com.isees.entities.Exam;
import com.isees.entities.Questiontable;
import com.isees.entities.StudentExam;
import com.isees.repos.QuestionRepository;
import com.isees.repos.StudentExamRepository;

@Service
public class PaperService {

    @Autowired
    private StudentExamRepository repo;

    @Autowired
    private QuestionRepository questionRepo;
    @Autowired
    private ResultEmailService resultEmailService;


    public String submitPaper(PaperSubmitDto dto) {

        int total = dto.getAnswers().size();
        int correctCount = 0;

        for (PaperSubmitDto.AnswerDto a : dto.getAnswers()) {

            Questiontable q = questionRepo.findById(a.getQuestionId())
                    .orElse(null);

            if (q == null) continue;

            boolean isCorrect = q.getCorrectAnswer()
                .equalsIgnoreCase(a.getSelectedOption());

            if (isCorrect) correctCount++;

            StudentExam exam = new StudentExam();
            exam.setStudentName(dto.getStudentName());
            exam.setEmail(dto.getEmail());
            exam.setQuestion(q.getQuestionText());
            exam.setCorrectAnswer(q.getCorrectAnswer());
            exam.setAnswer(a.getSelectedOption());
            exam.setQuestionId(q.getId());
            exam.setStatus(isCorrect ? "Correct" : "Wrong");

            // ✅ store score & total only on each row (last row contains final score)
            exam.setScore(correctCount);
            
            
            exam.setTotalQuestions(total);
            double setper = ((double) correctCount / total) * 100;
            exam.setPer(setper);

            
            repo.save(exam);
        }
     // ✅ After all answers are saved
        List<StudentExam> savedAnswers = repo.findByEmail(dto.getEmail());

        // ✅ Send result email
        try {
            resultEmailService.sendExamResultMail(
                dto.getEmail(),
                dto.getStudentName(),
                correctCount,
                total,
                savedAnswers
            );
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }

        return "✅ Exam submitted! Score: " + correctCount + "/" + total + 
               " (Result emailed to " + dto.getEmail() + ")";


    }

    public ExamResultDto getFormattedResultByEmail(String email) {

        List<StudentExam> list = repo.findByEmail(email);

        if (list.isEmpty()) return null;

        StudentExam last = list.get(list.size() - 1);

        ExamResultDto dto = new ExamResultDto();
        dto.setStudentName(last.getStudentName());
        dto.setEmail(last.getEmail());
        dto.setScore(last.getScore());
        dto.setTotalQuestions(last.getTotalQuestions());

        List<ExamResultDto.AnswerInfo> answers = new ArrayList<>();

        for (StudentExam s : list) {
            ExamResultDto.AnswerInfo info = new ExamResultDto.AnswerInfo();
            info.setQuestionText(s.getQuestion());
            info.setSelectedOption(s.getAnswer());
            info.setCorrectAnswer(s.getCorrectAnswer());
            answers.add(info);
        }

        dto.setAnswers(answers);

        return dto;
    }

	public List<StudentExam> getall() {
		
		return repo.findAll();
	}

	
    
    
    
}
