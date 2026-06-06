package com.isees.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.isees.entities.ActivityLog;

public interface ActivityLogRepo extends JpaRepository<ActivityLog, Long> {
}