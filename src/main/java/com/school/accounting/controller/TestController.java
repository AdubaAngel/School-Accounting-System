package com.school.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.accounting.repository.SchoolUserRepository;

@RestController
public class TestController {
    
    @Autowired
    private SchoolUserRepository userRepository;
    
    @GetMapping("/test")
    public String test() {
        long count = userRepository.count();
        return "Database connected! Found " + count + " users in the system.";
    }
}