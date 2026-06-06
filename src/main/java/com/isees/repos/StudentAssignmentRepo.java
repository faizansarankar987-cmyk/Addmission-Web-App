package com.isees.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.StudentAssignment;

@Repository
public interface StudentAssignmentRepo extends JpaRepository<StudentAssignment, Long> {

}
