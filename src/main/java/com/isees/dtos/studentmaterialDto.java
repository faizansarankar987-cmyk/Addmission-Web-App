package com.isees.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class studentmaterialDto {

    private String title;
    private String courseName;
    private String description;
    private String uploadedAt;

    private String fileUrl; // 🔥 required for do
}
