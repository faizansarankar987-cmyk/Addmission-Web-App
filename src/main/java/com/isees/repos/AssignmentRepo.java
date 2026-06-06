package com.isees.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.Assignments;

@Repository
public interface AssignmentRepo extends JpaRepository<Assignments, Long> {

    List<Assignments> findByEmail(String email);

    List<Assignments> findByCoursenameIn(List<String> courses);

}