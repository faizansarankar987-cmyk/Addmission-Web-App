package com.isees.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.isees.entities.Courses;
@Repository
public interface CoursesRepo extends JpaRepository<Courses, Long>{

	Optional<Courses> findByCoursename(String coursename);

}
