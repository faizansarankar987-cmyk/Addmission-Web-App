package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isees.dtos.ExamResultDto;
import com.isees.dtos.PaperSubmitDto;
import com.isees.entities.Application;
import com.isees.entities.Exam;
import com.isees.entities.StudentExam;
import com.isees.repos.ApplicationRepo;
import com.isees.services.ExamService;
import com.isees.services.PaperService;

@RestController
@RequestMapping("/exam")
@CrossOrigin(origins = "*")
public class PaperController {

	@Autowired
	private ExamService examService;
    @Autowired
    private PaperService service;
    @Autowired private ApplicationRepo applicationRepo;

    @PostMapping("/submit")
    public String submitExam(@RequestBody PaperSubmitDto dto) {
        return service.submitPaper(dto);
    }

    @GetMapping("/results/{email}")
    public ResponseEntity<?> getResult(@PathVariable String email) throws Exception {

        // ✅ Remove %0A and newline
        email = java.net.URLDecoder.decode(email, "UTF-8").trim();

        ExamResultDto result = service.getFormattedResultByEmail(email);

        if (result == null) {
            return ResponseEntity.status(404).body("No results found!");
        }

        return ResponseEntity.ok(result);  // ✅ returns JSON DTO
    }
    @GetMapping("/getall")
    public List<StudentExam> getall( ) {
		return service.getall();
    	
    }
   
    @GetMapping("/student-exams")
    public List<Exam> getStudentExams(@RequestParam String email) {

        List<Application> apps = applicationRepo.findAllByEmailid(email);

        if(apps.isEmpty()){
            return List.of();
        }

        List<String> courses = apps.stream()
                .map(Application::getCoursename)
                .toList();

        return examService.getExamByCourseList(courses);
    }
    
    
}
