package com.isees.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.studentmaterialDto;
import com.isees.entities.StudyMaterial;
import com.isees.repos.StudentMaterialRepo;

import jakarta.transaction.Transactional;

@Service
public class Studymaterialservi {
   
    @Autowired
    private StudentMaterialRepo repo;

    public StudyMaterial addMaterial(studentmaterialDto dto, MultipartFile file) throws Exception {

        String uploadDir = "C:/isees/uploads/";

        // Create folder if not exists
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        // Save file
        String fileName = file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        // Public URL for accessing file
        String fileUrl = "http://localhost:8080/uploads/" + fileName;

        // Save DB entry
        StudyMaterial material = new StudyMaterial();
        material.setTitle(dto.getTitle());
        material.setCourseName(dto.getCourseName());
        material.setDescription(dto.getDescription());
        material.setUploadedAt(dto.getUploadedAt());
        material.setFileUrl(fileUrl);  // 🔥 Store public URL, NOT local path

        return repo.save(material);
    }

    public List<StudyMaterial> getall() {
        return repo.findAll();
    }

	public List<StudyMaterial> getmaterial(String coursename) {
	List<StudyMaterial> list=	repo.findByCourseName(coursename);
	if(list.isEmpty()) {
		return 	null ; 
	}
	else {
	return list;
	}
	}
	 @Transactional  // <--- this is crucial
	public boolean delete(Long id) {
	    if (repo.existsById(id)) {
	        repo.deleteById(id);
	        return true;
	    }
	    return false;
	}
}
