package com.isees.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.StudyMaterial;
import java.util.List;



@Repository
public interface StudentMaterialRepo extends JpaRepository<StudyMaterial, Integer>{
	
List<StudyMaterial> findByCourseName(String courseName);



boolean existsById(Long id);



void deleteById(Long id);
}
