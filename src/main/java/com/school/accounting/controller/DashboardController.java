package com.school.accounting.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.accounting.model.SchoolUser;
import com.school.accounting.repository.SchoolUserRepository;

@Controller
public class DashboardController {

    private final SchoolUserRepository userRepository;

    public DashboardController(SchoolUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public String dashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SchoolUser user = userRepository.findByEmail(email).orElse(null);
        
        if (user != null) {
            // Calculate days until password expires (5 days from last change)
            long daysLeft = 5;
            if (user.getPasswordChangedAt() != null) {
                long daysSince = ChronoUnit.DAYS.between(user.getPasswordChangedAt(), LocalDateTime.now());
                daysLeft = Math.max(0, 5 - daysSince);
            }
            model.addAttribute("daysLeft", daysLeft);
            model.addAttribute("needsPasswordChange", daysLeft <= 0);
            model.addAttribute("userName", user.getFullName());
        }
        
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
    
    // ===== USER SELF PASSWORD CHANGE =====
    
    @GetMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes redirectAttributes) {
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SchoolUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Verify current password
        if (!encoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Current password is incorrect");
            return "redirect:/change-password";
        }
        
        // Check if new passwords match
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match");
            return "redirect:/change-password";
        }
        
        // Check minimum length
        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 6 characters");
            return "redirect:/change-password";
        }
        
        // Update password
        String hashedPassword = encoder.encode(newPassword);
        user.setPassword(hashedPassword);
        user.setPasswordChangedAt(LocalDateTime.now());
        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
        return "redirect:/dashboard";
    }
}