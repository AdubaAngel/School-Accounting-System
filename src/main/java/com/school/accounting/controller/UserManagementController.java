package com.school.accounting.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.school.accounting.model.SchoolUser;
import com.school.accounting.repository.SchoolUserRepository;

@Controller
public class UserManagementController {
    
    private final SchoolUserRepository userRepository;
    
    public UserManagementController(SchoolUserRepository userRepository) {
        this.userRepository = userRepository;
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
    public String saveUser(@ModelAttribute SchoolUser user) {
        System.out.println("=== SAVING USER ===");
        System.out.println("Username: '" + user.getUsername() + "'");
        System.out.println("FullName: '" + user.getFullName() + "'");
        System.out.println("Role: '" + user.getRole() + "'");
        System.out.println("Password (before set): '" + user.getPassword() + "'");
        
        user.setPassword("temp123");
        user.setCreatedAt(LocalDateTime.now());
        
        System.out.println("Password (after set): '" + user.getPassword() + "'");
        System.out.println("CreatedAt: " + user.getCreatedAt());
        
        try {
            userRepository.save(user);
            System.out.println("User saved successfully!");
        } catch (Exception e) {
            System.out.println("ERROR SAVING USER: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        
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
    public String deactivateUser(@PathVariable Long id) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole().equals("OWNER")) {
            throw new RuntimeException("Cannot deactivate the OWNER account");
        }
        
        user.setIsActive(false);
        userRepository.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/reactivate/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String reactivateUser(@PathVariable Long id) {
        SchoolUser user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setIsActive(true);
        userRepository.save(user);
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
                            @ModelAttribute SchoolUser updatedUser,
                            @RequestParam String role) {
        SchoolUser existing = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Don't allow role change for OWNER
        if (existing.getRole().equals("OWNER") && !role.equals("OWNER")) {
            throw new RuntimeException("Cannot change OWNER role");
        }
        
        // Update fields
        existing.setFullName(updatedUser.getFullName());
        existing.setRole(role);
        // Username cannot be changed (primary key)
        // Password change handled separately
        
        userRepository.save(existing);
        return "redirect:/users/list";
    }
}