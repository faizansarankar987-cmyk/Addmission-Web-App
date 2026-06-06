package com.isees.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.Exam;
import com.isees.entities.Questiontable;
import com.isees.repos.ExamRepository;
import com.isees.repos.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepo;

    @Autowired
    private ExamRepository examRepo;

    public String addQuestions(Long examId, List<Questiontable> questions) {

        Exam exam = examRepo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        for (Questiontable q : questions) {
            q.setExam(exam);
        }

        questionRepo.saveAll(questions);
        return questions.size() + " questions added to exam " + examId;
    }

    public List<Questiontable> getQuestionsByExam(Long examId) {
        return questionRepo.findByExam_Id(examId);   // ✅ FIXED
    }
    
    public String updateQuestion(Long questionId, Questiontable updatedQ) {

        Questiontable q = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        q.setQuestionText(updatedQ.getQuestionText());
        q.setOption1(updatedQ.getOption1());
        q.setOption2(updatedQ.getOption2());
        q.setOption3(updatedQ.getOption3());
        q.setOption4(updatedQ.getOption4());
        q.setCorrectAnswer(updatedQ.getCorrectAnswer());

        questionRepo.save(q);

        return "Question updated successfully!";
    }

    public String updateMultipleQuestions(List<Questiontable> questions) {
        questionRepo.saveAll(questions);
        return questions.size() + " questions updated successfully!";
    }

    public String updateAllQuestions(Long examId, List<Questiontable> newQuestions) {

        Exam exam = examRepo.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        questionRepo.deleteByExam_Id(examId);   // ✅ FIX IF YOU WANT

        newQuestions.forEach(q -> q.setExam(exam));
        questionRepo.saveAll(newQuestions);

        return "All questions updated successfully for exam " + examId;
    }

	public long countAll(){
    return questionRepo.count();
}

	public String addSingleQuestion(Long examId, Questiontable q) {
	    Exam exam = examRepo.findById(examId)
	            .orElseThrow(() -> new RuntimeException("Exam not found"));

	    q.setExam(exam);
	    questionRepo.save(q);

	    return "Question added successfully!";
	}

	public Questiontable getOne(Long id) {
	    return questionRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Question not found"));
	}



}
