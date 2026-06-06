package com.isees.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isees.entities.CertificateEntity;

public interface CertificateRepo
        extends JpaRepository<CertificateEntity, Long> {

//    Optional<CertificateEntity> findByEmailAndCourseName(
//            String email, String courseName);
    
    Optional<CertificateEntity> findByEmailAndCourseName(String email, String courseName);

    boolean existsByEmailAndCourseName(String email, String courseName);
    
}
