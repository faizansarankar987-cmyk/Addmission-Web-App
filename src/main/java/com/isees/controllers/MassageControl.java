package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isees.dtos.MassageDto;
import com.isees.entities.MassagesEntity;
import com.isees.services.MassageSer;

@RestController
@RequestMapping("/massage")
@CrossOrigin("*")
public class MassageControl {
	@Autowired MassageSer s;
	
	@PostMapping("/sendmassage")
	
	public ResponseEntity<?> postmassage(@RequestBody MassageDto dto) {
	    return s.save(dto);
	}
	
	@GetMapping("/getmessages")
	 
	public List<MassagesEntity> get() {
		return s.getall();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletemessage(@PathVariable Long id) {
	    s.delete(id);
	    return ResponseEntity.ok("Message deleted");
	}

	@GetMapping("/latest")
	public List<MassagesEntity> latestMessages(){
	    return s.latest5();
	}

	@PutMapping("/read/{id}")
	public ResponseEntity<String> markAsRead(@PathVariable Long id){
	    s.markAsRead(id);
	    return ResponseEntity.ok("Marked as read");
	}
}
