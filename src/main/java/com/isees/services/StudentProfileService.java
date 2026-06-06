package com.isees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isees.entities.StudentProfile;
import com.isees.repos.StudentProfileRepo;
import java.util.List;

@Service
public class StudentProfileService {

    @Autowired
    StudentProfileRepo repo;

    public List<String> getCoursesByEmail(String email) {

        StudentProfile profile = repo.findByEmail(email);

        if (profile == null || profile.getEnrolledCourses() == null) {
            return List.of(); // 🔥 prevent crash
        }

        return profile.getEnrolledCourses();
    }
}