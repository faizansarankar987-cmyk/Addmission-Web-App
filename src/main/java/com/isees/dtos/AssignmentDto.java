package com.isees.dtos;

import lombok.Data;

@Data
public class AssignmentDto {

    private String coursename;      // which course
    private String subject;         // subject name

    private String title;           // assignment title
    private String description;     // details/instructions

    private String fileUrl;         // PDF / DOC link
//    private String uploadDate;      // auto set when added

    private String dueDate;  
}
