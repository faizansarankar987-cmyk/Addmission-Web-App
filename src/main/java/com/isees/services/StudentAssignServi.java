package com.isees.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.StudentAssignmentdto;
import com.isees.entities.Assignments;
import com.isees.entities.StudentAssignment;
import com.isees.repos.AssignmentRepo;
import com.isees.repos.StudentAssignmentRepo;

@Service
public class StudentAssignServi {

    @Autowired
    private StudentAssignmentRepo studentrepo;

    @Autowired
    private AssignmentRepo assignmentrepo;

    public List<Assignments> getall() {
        return assignmentrepo.findAll();
    }

    public StudentAssignment uploadassignment(StudentAssignmentdto dto, MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("No file uploaded");
        }

        // Save file to local folder
        String uploadDir = "C:/isees/uploads/";
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String filePath = Paths.get(uploadDir, file.getOriginalFilename()).toString();
        file.transferTo(new File(filePath));

        String fileUrl = "http://localhost:8080/uploads/" + file.getOriginalFilename();

        // Get assignment entity from DB
        Assignments mainAssignment = assignmentrepo.findById(dto.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Invalid Assignment ID"));

        // Create student assignment entity
        StudentAssignment studentAssignment = new StudentAssignment();
        studentAssignment.setAssignment(mainAssignment); // Set ManyToOne relation
        studentAssignment.setStudentEmail(dto.getStudentEmail());
        studentAssignment.setStudentName(dto.getStudentName());
        studentAssignment.setUploadDate(java.time.LocalDate.now().toString());
        studentAssignment.setFileUrl(fileUrl);

        return studentrepo.save(studentAssignment);
    }
}
