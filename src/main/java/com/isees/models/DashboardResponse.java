package com.isees.models;

import java.util.List;
import lombok.Data;

@Data
public class DashboardResponse {
    private String studentname;
    private List<ApplicationSummary> applications;
}