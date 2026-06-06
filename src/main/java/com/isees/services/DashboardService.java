package com.isees.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.Application;
import com.isees.repos.ApplicationRepo;

@Service
public class DashboardService {

    @Autowired
    private ApplicationRepo applicationRepo;

    // ✅ Returns all applications for one email (not just one)
    public List<Application> getApplicationsByEmail(String email) {
        return applicationRepo.findAllByEmailid(email);
    }

	
}
