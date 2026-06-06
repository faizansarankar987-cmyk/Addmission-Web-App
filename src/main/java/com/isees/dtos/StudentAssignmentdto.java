package com.isees.dtos;

import lombok.Data;

@Data
public class StudentAssignmentdto {
    private Long assignmentId;   // if you link to Assignments table
    private String studentEmail;
    private String studentName;
    private String coursename;
    private String subject;
}
