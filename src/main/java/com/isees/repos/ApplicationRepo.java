package com.isees.repos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isees.dtos.CoursePopularityDto;
import com.isees.entities.Application;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, Long> {

//    // ✅ For checking duplicates (used in form validation)
//    Optional<Application> findByStudentname(String studentname);
//
//    // ✅ For listing all applications by student
//    List<Application> findAllByStudentname(String studentname);
//   
//    Optional<Application> findByEmailid(String emailid);
//   
//    List<Application> findAllByEmailid(String emailid);
//
//	Application findByEmailidAndCoursename(String email, String course);
//	Optional<Application> findTopByEmailidOrderByIdDesc(String emailid);
//
//	
//	
//	@Query("SELECT a.coursename FROM Application a WHERE a.email = :email")
//    List<String> findCoursesByEmail(@Param("email") String email);
//    List<Application> findByEmailid(String emailid);

	
	    // check duplicate student
	    Optional<Application> findByStudentname(String studentname);

	    // all applications by student name
	    List<Application> findAllByStudentname(String studentname);

	    // single application by email
	    Optional<Application> findByEmailid(String emailid);

	    // all courses by email
	    List<Application> findAllByEmailid(String emailid);

	    // check if student already applied for course
	    Application findByEmailidAndCoursename(String emailid, String coursename);

	    // latest application
	    Optional<Application> findTopByEmailidOrderByIdDesc(String emailid);

		List<Application> findTop5ByOrderByIdDesc();
	    
		@Query("SELECT SUM(a.remainingFees) FROM Application a")
		Double getTotalPendingFees();
		
		@Query("""
				SELECT COALESCE(SUM(a.amountPaid),0)
				FROM Application a
				WHERE a.date BETWEEN :start AND :end
				""")
				Double getMonthlyIncome(LocalDateTime start, LocalDateTime end);
		
		
		@Query("""
				SELECT new com.isees.dtos.CoursePopularityDto(a.coursename, COUNT(a.id))
				FROM Application a
				GROUP BY a.coursename
				""")
				List<CoursePopularityDto> getCoursePopularity();


		 List<Application> findByDateBetween(LocalDateTime from, LocalDateTime to);

		    List<Application> findByPaymentDateBetween(LocalDateTime from, LocalDateTime to);	}

