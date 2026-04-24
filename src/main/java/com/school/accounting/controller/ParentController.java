package com.school.accounting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.school.accounting.model.Parent;
import com.school.accounting.repository.ParentRepository;

@Controller
public class ParentController {
    private final ParentRepository parentRepository;

    public ParentController(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }
    
    @GetMapping("parents/add")
    @PreAuthorize("hasRole('OWNER')")
    public String showAddForm(Model model) {
        model.addAttribute("parent", new Parent());
        return "add-parent";
    }

     @PostMapping("/parents/add")
     @PreAuthorize("hasRole('OWNER')")
    public String saveParent(@ModelAttribute Parent parent) {
        // Save parent
        parentRepository.save(parent);
        return "redirect:/dashboard";
    }

    @GetMapping("/parents/list")
    @PreAuthorize("hasAnyRole('OWNER', 'ACCOUNTANT', 'AUDITOR')")
    public String listParents(Model model) {
        model.addAttribute("parents", parentRepository.findAll());
        return "parents-list";
}
}
