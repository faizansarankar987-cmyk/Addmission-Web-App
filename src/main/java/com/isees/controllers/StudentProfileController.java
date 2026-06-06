package com.isees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.isees.services.StudentProfileService;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin("*")
public class StudentProfileController {

    @Autowired
    StudentProfileService service;

    @GetMapping("/{email}/courses")
    public List<String> getCourses(@PathVariable String email) {
        return service.getCoursesByEmail(email);
    }
}