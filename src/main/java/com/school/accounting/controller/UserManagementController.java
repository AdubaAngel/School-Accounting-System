package com.school.accounting.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.accounting.model.SchoolUser;
import com.school.accounting.repository.SchoolUserRepository;

@Controller
public class UserManagementController {
    
    private final SchoolUserRepository userRepository;
    
    public UserManagementController(SchoolUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String generateEmail(String fullName) {
        String[] nameParts = fullName.trim().split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";
        
        String base = firstName.substring(0, 1).toLowerCase() + lastName.toLowerCase();
        long count = userRepository.countByEmailStartingWith(base);
        
        return base + (count + 1) + "@marvangel.com";
    }
    
    @GetMapping("/users/add")
    @PreAuthorize("hasRole('OWNER')")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new SchoolUser());
        model.addAttribute("roles", List.of("ACCOUNTANT", "AUDITOR"));
        return "add-user";
    }

    @PostMapping("/users/add")
    @PreAuthorize("hasRole('OWNER')")
    public String saveUser(@RequestParam String fullName,
                           @RequestParam String role,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        
        String email = generateEmail(fullName);
        String username = email;
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);
        
        SchoolUser user = new SchoolUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setRole(role);
        user.setPassword(hashedPassword);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setPasswordChangedAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("successMessage", 
            "User created successfully! Login Email: " + email);
        
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    @PreAuthorize("hasRole('OWNER')")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users-list";
    }

    @GetMapping("/users/deactivate/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deactivateUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole().equals("OWNER")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot deactivate the OWNER account");
            return "redirect:/users/list";
        }
        
        user.setIsActive(false);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User deactivated successfully");
        return "redirect:/users/list";
    }

    @GetMapping("/users/reactivate/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String reactivateUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setIsActive(true);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User reactivated successfully");
        return "redirect:/users/list";
    }
    
    @GetMapping("/users/edit/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        model.addAttribute("user", user);
        model.addAttribute("roles", List.of("ACCOUNTANT", "AUDITOR"));
        return "edit-user";
    }

    @PostMapping("/users/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String updateUser(@PathVariable Long id, 
                            @RequestParam String fullName,
                            @RequestParam String role,
                            RedirectAttributes redirectAttributes) {
        SchoolUser existing = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Don't allow role change for OWNER
        if (existing.getRole().equals("OWNER") && !role.equals("OWNER")) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot change OWNER role");
            return "redirect:/users/list";
        }
        
        // Don't allow name change for OWNER
        if (existing.getRole().equals("OWNER") && !fullName.equals(existing.getFullName())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot change OWNER name");
            return "redirect:/users/list";
        }
        
        existing.setFullName(fullName);
        existing.setRole(role);
        
        userRepository.save(existing);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        return "redirect:/users/list";
    }

    // ===== OWNER ONLY: Reset other users' passwords =====
    
    @GetMapping("/users/reset-password/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String showResetPasswordForm(@PathVariable Long id, Model model) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        model.addAttribute("user", user);
        return "reset-password";
    }

    @PostMapping("/users/reset-password/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String resetPassword(@PathVariable Long id,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                RedirectAttributes redirectAttributes) {
        
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match");
            return "redirect:/users/reset-password/" + id;
        }
        
        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 6 characters");
            return "redirect:/users/reset-password/" + id;
        }
        
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(newPassword);
        user.setPassword(hashedPassword);
        user.setPasswordChangedAt(LocalDateTime.now());
        
        userRepository.save(user);
        
        redirectAttributes.addFlashAttribute("successMessage", 
            "Password reset successfully for user: " + user.getFullName());
        
        return "redirect:/users/list";
    }
}