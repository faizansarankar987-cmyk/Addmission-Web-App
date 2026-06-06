package com.isees.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isees.entities.ActivityLog;
import com.isees.repos.ActivityLogRepo;


@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepo repo;

    public ActivityLog save(ActivityLog log){
        return repo.save(log);
    }

    public void log(String username, String module, String action){
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setModule(module);
        log.setAction(action);
        repo.save(log);
    }

    public List<ActivityLog> getLatest(){
        return repo.findAll()
                   .stream()
                   .sorted((a,b)->b.getCreatedAt().compareTo(a.getCreatedAt()))
                   .limit(10)
                   .toList();
    }
}