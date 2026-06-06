package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isees.dtos.ApplicationDto;
import com.isees.dtos.CoursesDto;
import com.isees.entities.Courses;
import com.isees.services.CoursesService;

@RestController
@RequestMapping("/courses")
@CrossOrigin("*") // Allow all origins

public class CoursesController {

	@Autowired CoursesService s2;
	
	@PostMapping("/add")
	public String Addingcourses(@RequestBody CoursesDto d2) {
		String add=s2.addcourse(d2);
		if(add.isEmpty()) {
			return "somthing gone wrong";
		}
		return" courses added succssefully";
	}
	@GetMapping("/get")
	public List<Courses> getall(){
		return s2.courseslist();
	}
	@GetMapping("/count")
	public long countCourses() {
	    return s2.courseslist().size();
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
	    s2.deletecourse(id);
	    return ResponseEntity.ok("Deleted");
	}

}
