package com.school.accounting.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parents")
public class Parent{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;; 

    @Column(name = "name", nullable = false)
    private String name; 
    
    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "parent")
    private List<Student> students;
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail(){return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone(){return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    
}

