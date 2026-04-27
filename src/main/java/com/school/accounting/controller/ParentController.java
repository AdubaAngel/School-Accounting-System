package com.school.accounting.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.school.accounting.model.Parent;
import com.school.accounting.model.Student;
import com.school.accounting.repository.ParentRepository;
import com.school.accounting.repository.StudentRepository;

@Controller
public class ParentController {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    public ParentController(ParentRepository parentRepository, StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
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

    @GetMapping("/parents/edit/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Parent parent = parentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        model.addAttribute("parent", parent);
        return "edit-parent";
    }

    @PostMapping("/parents/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String updateParent(@PathVariable Long id, @ModelAttribute Parent updatedParent) {
        Parent existing = parentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        existing.setName(updatedParent.getName());
        existing.setEmail(updatedParent.getEmail());
        existing.setPhone(updatedParent.getPhone());
        
        parentRepository.save(existing);
        return "redirect:/parents/list";
    }

    @GetMapping("/parents/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deleteParent(@PathVariable Long id) {
        if (!parentRepository.existsById(id)) {
            throw new RuntimeException("Parent not found");
        }
        
        List<Student> students = studentRepository.findByParentId(id);
        if (!students.isEmpty()) {
            throw new RuntimeException("Cannot delete parent with " + students.size() + " existing students. Transfer or delete students first.");
        }
        
        parentRepository.deleteById(id);
        return "redirect:/parents/list";
    }
}
