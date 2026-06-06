package com.isees.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.MassagesEntity;
@Repository
public interface MassageRepo extends JpaRepository<MassagesEntity, Long>{
    List<MassagesEntity> findTop5ByOrderByCreatedAtDesc();

}
