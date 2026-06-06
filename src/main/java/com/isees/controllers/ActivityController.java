package com.isees.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.isees.entities.ActivityLog;
import com.isees.services.ActivityLogService;

@RestController
@RequestMapping("/activity")
@CrossOrigin("*")
public class ActivityController {

    @Autowired
    private ActivityLogService service;

    @GetMapping("/latest")
    public List<ActivityLog> latest(){
        return service.getLatest();
    }
    
    @GetMapping("/test")
    public ActivityLog test(){
        ActivityLog log = new ActivityLog();
        log.setUsername("ADMIN");
        log.setModule("TEST");
        log.setAction("Activity test log created");
        return service.save(log);
    }
}