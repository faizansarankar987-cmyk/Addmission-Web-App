package com.isees.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.Exam;
import com.isees.repos.ExamRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepo;

    public Exam createExam(Exam exam) {
        return examRepo.save(exam);
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepo.findById(id);
    }

    public List<Exam> getAll() {
        return examRepo.findAll();
    }

    public List<Exam> getExamByCourse(String course){
        return examRepo.findByCourseName(course);
    }
    
    public List<Exam> getExamByCourseList(List<String> courses){
        return examRepo.findByCourseNameIn(courses);
    }

	public void deleteExam(Long id) {
		// TODO Auto-generated method stub
		examRepo.deleteById(id);
	}
}
