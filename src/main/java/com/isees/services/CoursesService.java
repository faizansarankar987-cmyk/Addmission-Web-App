package com.isees.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isees.controllers.Controller1;
import com.isees.dtos.ApplicationDto;
import com.isees.dtos.CoursesDto;
import com.isees.entities.Courses;
import com.isees.repos.ApplicationRepo;
import com.isees.repos.CoursesRepo;

@Service
public class CoursesService {

    private final Controller1 controller1;

//  private final ApplicationService applicationService;
@Autowired CoursesRepo r2;
@Autowired ApplicationRepo r3;

    CoursesService(Controller1 controller1) {
        this.controller1 = controller1;
    }

//    CoursesService(ApplicationService applicationService) {
//        this.applicationService = applicationService;
//    }

public String addcourse(CoursesDto d2) {
	Courses e2=new Courses();
	if(d2.getCoursename()==null || d2.getCoursename().trim().isEmpty()) {
	return "please select the course ";
	}
	
	e2.setCoursename(d2.getCoursename());
	//e2.setTotalseats(d2.getTotalseats());	
	e2.setDuration(d2.getDuration());
	//e2.setEligibility(d2.getEligibility());
	e2.setFees(d2.getFees());
	r2.save(e2);
	return "Course added succssesfully";
	
	

}
//public List<Courses>seats(ApplicationDto rd2,CoursesDto d2){
//	if(rd2.getConfirmseats()!=null) {
//	d2.setAvailableseat(d2.getTotalseats()-rd2.getConfirmseats());;
//	}
//	return r2.findAll();
//}
public List<Courses> courseslist(){
	return r2.findAll();
}


public boolean deletecourse(Long id) {
    if (r2.existsById(id)) {
        r2.deleteById(id);
        return true;
    }
    return false;
}





	
}

