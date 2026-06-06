package com.isees.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.Questiontable;
import jakarta.transaction.Transactional;
@Repository
public interface QuestionRepository extends JpaRepository<Questiontable, Long> {

    @Transactional
    void deleteByExam_Id(Long examId);

    List<Questiontable> findByExam_Id(Long examId);
    
}
