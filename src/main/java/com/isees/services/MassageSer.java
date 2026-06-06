package com.isees.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.isees.dtos.MassageDto;
import com.isees.entities.MassagesEntity;
import com.isees.repos.MassageRepo;

@Service
public class MassageSer {
@Autowired MassageRepo r;
public ResponseEntity<?> save(MassageDto dto) {

    if (dto.getMassage() == null || dto.getMassage().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("Message cannot be empty");
    }

    MassagesEntity e = new MassagesEntity();
    e.setMassage(dto.getMassage());

    // ✅ THIS WAS MISSING
    r.save(e);

    return ResponseEntity.ok("Message send successfully");
}
public List<MassagesEntity> getall() {
	return r.findAll();
}
public void delete(Long id) {
    if(r.existsById(id)) {
        r.deleteById(id);
    }
}

public List<MassagesEntity> latest5() {
    return r.findTop5ByOrderByCreatedAtDesc();
}

public void markAsRead(Long id) {
    MassagesEntity msg = r.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));

    msg.setReadStatus(true);
    r.save(msg);
}   

}
