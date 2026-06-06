package com.isees.services;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.AssignmentDto;
import com.isees.entities.Application;
import com.isees.entities.Assignments;
import com.isees.repos.ApplicationRepo;
import com.isees.repos.AssignmentRepo;

import jakarta.transaction.Transactional;

@Service
public class AssignmentServi {

	@Autowired
	ApplicationRepo applicationRepo;

	@Autowired
	AssignmentRepo assignmentrepo;

	public List<Assignments> getAssignmentsForStudent(String email){

	    List<Application> apps = applicationRepo.findAllByEmailid(email);

	    List<String> courses = apps.stream()
	            .map(Application::getCoursename)
	            .toList();

	    return assignmentrepo.findByCoursenameIn(courses);
	}
    // 🔥 Upload Assignment (Teacher/Admin)
    public Assignments addassignment(AssignmentDto dto, MultipartFile file) throws Exception {

        String uploadDir = "C:/isees/uploads/";

        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        file.transferTo(new File(filePath));

        String fileUrl = "http://localhost:8080/uploads/" + fileName;

        Assignments assignment = new Assignments();
        assignment.setTitle(dto.getTitle());
        assignment.setCoursename(dto.getCoursename());
        assignment.setDescription(dto.getDescription());
        assignment.setSubject(dto.getSubject());
        assignment.setDueDate(dto.getDueDate());
        assignment.setFileUrl(fileUrl);

        return assignmentrepo.save(assignment);
    }

    // 🔥 Get Assignments by Multiple Courses
//    public List<Assignments> getAssignmentsByCourses(List<String> courses){
//        return assignmentrepo.findByEmail(courses);
//    }

    @Transactional
    public boolean delete(Long assignmentId) {
        if (assignmentrepo.existsById(assignmentId)) {
            assignmentrepo.deleteById(assignmentId);
            return true;
        }
        return false;
    }

	public List<Assignments> get() {
		// TODO Auto-generated method stub
	return	assignmentrepo.findAll();
	}

	public List<Assignments> getAssignmentsByEmail(String email){
	    return assignmentrepo.findByEmail(email);
	}
	public List<Assignments> getAssignmentByStudent(String email){
		return assignmentrepo.findByEmail(email);
	}


//	public List<Assignments> getAssignmentsByStudentEmail(String email){
//
//	    List<String> courses = assignmentrepo.findCoursesByEmail(email);
//
//	    return assignmentrepo.findByCoursenameIn(courses);
//	}

}