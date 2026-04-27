package com.school.accounting.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.school.accounting.model.Parent; 
import com.school.accounting.model.Student;
import com.school.accounting.repository.ParentRepository;
import com.school.accounting.repository.StudentRepository;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;

    public StudentController(StudentRepository studentRepository, ParentRepository parentRepository) {
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
    }

    // Constructor with @Autowired (or use field injection – your choice)

    @GetMapping("/students/add")
    @PreAuthorize("hasRole('OWNER')")
    public String showAddForm(Model model) {
        // Add empty Student object to form
        model.addAttribute("student", new Student());
        // Add list of parents for dropdown
        model.addAttribute("parents", parentRepository.findAll());
        // Add list of class levels for dropdown
        List<String> classLevels = Arrays.asList("PRESCHOOL", "KG", "GRADE_1", "GRADE_2", "GRADE_3", "GRADE_4", "GRADE_5", "GRADE_6");
        model.addAttribute("classLevels", classLevels);
        return "add-student";
    }

    @PostMapping("/students/add")
    @PreAuthorize("hasRole('OWNER')")
    public String saveStudent(@ModelAttribute Student student, @RequestParam Long parentId) {
        // Find parent by ID
        Parent parent = parentRepository.findById(parentId).orElseThrow(() -> new RuntimeException("Parent not found"));
        // Set parent on student
        student.setParent(parent);
        // Save student
        studentRepository.save(student);
        return "redirect:/dashboard";
    }

    @GetMapping("/students/class/{classLevel}")
    @PreAuthorize("hasAnyRole('OWNER', 'ACCOUNTANT', 'AUDITOR')")
    public String showStudentsByClass(@PathVariable String classLevel, Model model) {
        // Find students by class level
        List<Student> students = studentRepository.findByClassLevel(classLevel);
        // Remove the if statement entirely
        // Just pass the empty list to the template
        model.addAttribute("students", students);
        model.addAttribute("classLevel", classLevel);
        return "students-by-class";
    }


    @GetMapping("/students/edit/{id}")
@PreAuthorize("hasRole('OWNER')")
public String showEditForm(@PathVariable Long id, Model model) {
    Student student = studentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Student not found"));
    
    model.addAttribute("student", student);
    model.addAttribute("parents", parentRepository.findAll());
    model.addAttribute("classLevels", Arrays.asList("PRESCHOOL", "KG", "GRADE_1", "GRADE_2", "GRADE_3", "GRADE_4", "GRADE_5", "GRADE_6"));
    return "edit-student";
}

    @PostMapping("/students/update/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String updateStudent(@PathVariable Long id, 
                                @ModelAttribute Student updatedStudent,
                                @RequestParam Long parentId) {
        Student existing = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        Parent parent = parentRepository.findById(parentId)
            .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        existing.setFullName(updatedStudent.getFullName());
        existing.setClassLevel(updatedStudent.getClassLevel());
        existing.setParent(parent);
        
        studentRepository.save(existing);
        return "redirect:/students/class/" + existing.getClassLevel();
    }

    @GetMapping("/students/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found"));
        
        String classLevel = student.getClassLevel();
        studentRepository.deleteById(id);
        
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully.");
        return "redirect:/students/class/" + classLevel;
    }
}