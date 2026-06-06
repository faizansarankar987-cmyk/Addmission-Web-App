package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.AssignmentDto;
import com.isees.entities.Assignments;
import com.isees.services.AssignmentServi;

@RestController
@RequestMapping("/Assignments")
@CrossOrigin("*")
public class AssignmentControl {

    @Autowired
    AssignmentServi servi;

    // ✅ Upload (Teacher/Admin)
    @PostMapping("/upload")
    public ResponseEntity<?> uploadMaterial(
            @RequestPart("data") AssignmentDto dto,
            @RequestPart("file") MultipartFile file) {
        try {
            servi.addassignment(dto, file);
            return ResponseEntity.ok("Assignment uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // ✅ Get assignments by multiple courses (Student)
//    @PostMapping("/getByCourses")
//    public List<Assignments> getByCourses(@RequestBody String courses){
//        return servi.getAssignmentsByCourses(courses);
//    }
    @GetMapping("/getByEmail/{email}")
    public List<Assignments> getAssignmentsByEmail(@PathVariable String email){
        return servi.getAssignmentsByEmail(email);
    }
    @GetMapping("/get")
    	List<Assignments>all(){
    		return servi.get();
    	}
    @GetMapping("/get/student-Assignmnet")
	public List<Assignments> getAssignmnet(@RequestParam String email){
		return servi.getAssignmentByStudent(email);
	}
    

    // ✅ Delete
    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long assignmentId) {

        boolean deleted = servi.delete(assignmentId);

        if (deleted) {
            return ResponseEntity.ok("Assignment deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Assignment not found");
        }
    }
    
    
    @GetMapping("/student-assignments")
    public List<Assignments> getAssignments(@RequestParam String email){
        return servi.getAssignmentsForStudent(email);
    }
   
}