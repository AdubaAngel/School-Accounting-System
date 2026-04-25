package com.school.accounting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
    return "access-denied";
    }
@GetMapping("/hash")
@ResponseBody
public String getHash() {
    return new BCryptPasswordEncoder().encode("MarvAngel540");
}
}