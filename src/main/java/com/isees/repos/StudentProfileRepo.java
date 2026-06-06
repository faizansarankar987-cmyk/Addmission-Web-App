package com.isees.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isees.entities.StudentProfile;

public interface StudentProfileRepo extends JpaRepository<StudentProfile, Long> {

    StudentProfile findByEmail(String email);
}