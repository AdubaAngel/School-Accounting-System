package com.school.accounting.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showStudentsByClass(@PathVariable String classLevel, Model model) {
        // Find students by class level
        List<Student> students = studentRepository.findByClassLevel(classLevel);
        if (students.isEmpty()) {
            throw new RuntimeException("Students not found");
        }
        // Add to model
        model.addAttribute("students", students);
        model.addAttribute("classLevel", classLevel);
        return "students-by-class";
    }
}