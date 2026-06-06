package com.isees.repos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.Exam;
import com.isees.entities.ExamStatus;
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByCourseName(String course);

    List<Exam> findByCourseNameIn(List<String> courses);
    List<Exam> findByStatus(ExamStatus status);


}