package com.isees.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.studentmaterialDto;
import com.isees.entities.StudyMaterial;
import com.isees.services.Studymaterialservi;

import lombok.Delegate;

@RestController
@RequestMapping("/studymaterial")
public class StudyMaterialControll {

    @Autowired
    Studymaterialservi service;

    @PostMapping("/add")
    public ResponseEntity<?> uploadMaterial(
            @RequestPart("data") studentmaterialDto dto,
            @RequestPart("file") MultipartFile file) {

        try {
            StudyMaterial saved = service.addMaterial(dto, file);
            return ResponseEntity.ok("Material uploaded successfully");
        } 
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public List<StudyMaterial> getAll() {
        return service.getall();
    }
    @GetMapping("/get1/{coursename}")
    public List<StudyMaterial>getlist(@PathVariable String coursename){
		return service.getmaterial(coursename);
    	
    }
    
    
    @DeleteMapping("/delete/{id}")
    
    public String delete(@PathVariable Long id) {
    	service.delete(id);
		return "deleted succsessfully !";
    	
    }
}
