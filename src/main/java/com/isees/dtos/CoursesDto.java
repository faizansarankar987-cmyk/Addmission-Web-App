package com.isees.dtos;

import java.util.List;

import lombok.Data;

@Data
public class CoursesDto {
	private String coursename;
	private Long totalseats;
	private Long availableseat;
    private Long confirmseats = 0L; // 👈 new field (default 0)
    private String duration;
    private String eligibility;
    private Double fees; // ✅ MUST BE HERE

	List<ApplicationDto>applicationList;
	
}
