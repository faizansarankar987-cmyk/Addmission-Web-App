package com.isees.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.isees.dtos.AdminEntityDto;
import com.isees.entities.AdminEntity;
import com.isees.entities.Entity1;
import com.isees.repos.AdminLoginRepo;

@Service
public class AdminLoginServ {
@Autowired AdminLoginRepo adminrepo;
@Autowired
private PasswordEncoder encoder;

public String createadmin(AdminEntityDto adDto) {
	AdminEntity e=new AdminEntity();
	Optional<AdminEntity> exists=adminrepo.findByAdminusername(adDto.getAdminusername());
	if (exists.isPresent()) return "Email already exists!";
	if(adDto.getAdminusername()==null || adDto.getAdminusername().isBlank()) return	"username already exists or invalid try again";
		
	
	if(adDto.getAdminpass()==null|| adDto.getAdminpass().isBlank()) 	return	"username already exists or invalid try again";

	
	
	e.setAdminusername(adDto.getAdminusername());
	e.setAdminpass(encoder.encode(adDto.getAdminpass()));
	adminrepo.save(e);
	return "you have register";
	
}

public ResponseEntity<String> login(AdminEntityDto adDto) {
//	AdminEntity e=new AdminEntity();
	Optional<AdminEntity> find=adminrepo.findByAdminusername(adDto.getAdminusername());

	 if (find.isEmpty()) {
		 return ResponseEntity.status(404).body("Wrong username!");
     }
	 AdminEntity e = find.get();
	 if(encoder.matches(adDto.getAdminpass(), e.getAdminpass())) {
		 return ResponseEntity.ok("login successfull") ;
	 }
	 
	 else {
         return ResponseEntity.status(401).body("Wrong password!");
     }
	
	
}



	
}
