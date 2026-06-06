package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.AssignmentDto;
import com.isees.dtos.StudentAssignmentdto;
import com.isees.entities.Assignments;
import com.isees.entities.StudentAssignment;
import com.isees.services.StudentAssignServi;

@RestController
@RequestMapping("/studentassignment")
public class StudentassignControl {
	@Autowired StudentAssignServi servi;
	
	@GetMapping("/get")
	public List<Assignments> get() {
		return servi.getall();
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadMaterial( @RequestPart("data") StudentAssignmentdto dto,  @RequestPart("file") MultipartFile file) {
		try {
			StudentAssignment saved= servi.uploadassignment(dto, file);
//			if(saved!=null) 
			return ResponseEntity.ok("Material uploaded successfully");
		}
		catch(Exception e) {
			
		
	        return ResponseEntity.status(500).body("Error: " + e.getMessage());

		}
	}

}
