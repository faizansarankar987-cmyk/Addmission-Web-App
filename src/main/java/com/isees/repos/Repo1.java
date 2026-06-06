package com.isees.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.Entity1;

import java.util.Optional;

@Repository
public interface Repo1 extends JpaRepository<Entity1, Long>{

	Optional<Entity1> findByEmail(String email);
	String  findByPassword(String password);
	 
//	String findByEmail();

}
