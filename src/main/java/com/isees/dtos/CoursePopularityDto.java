package com.isees.dtos;

public class CoursePopularityDto {
    private String courseName;
    private Long students;

    public CoursePopularityDto(String courseName, Long students) {
        this.courseName = courseName;
        this.students = students;
    }

    public String getCourseName() { return courseName; }
    public Long getStudents() { return students; }
}