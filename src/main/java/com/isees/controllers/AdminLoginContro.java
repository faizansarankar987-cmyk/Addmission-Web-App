package com.isees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Admin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isees.dtos.AdminEntityDto;
import com.isees.dtos.Dto1;
import com.isees.services.AdminLoginServ;

@RestController
@RequestMapping("/admin")
public class AdminLoginContro {
@Autowired AdminLoginServ adminservi;
@PostMapping("/registeradmin")
public ResponseEntity<String> register(@RequestBody AdminEntityDto adDto) {
    return ResponseEntity.ok(adminservi.createadmin(adDto));
}

@PostMapping("/loginadmin")
public ResponseEntity<String> login(@RequestBody AdminEntityDto adDto){
	return adminservi.login(adDto);
}
	
}

