package com.isees.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isees.entities.AdminEntity;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdminLoginRepo extends JpaRepository<AdminEntity, Long> {

	public boolean existsByAdminusername(String adminusername);
	public boolean existsByAdminpass(String adminpass);
	public Optional<AdminEntity> findByAdminusername(String adminusername);
}
