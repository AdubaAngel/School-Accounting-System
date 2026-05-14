package com.school.accounting.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentTest {

    private Student student;
    private Parent parent;

    @BeforeEach
    void setUp() {
        parent = new Parent();
        parent.setId(1L);
        parent.setName("John Doe");
        
        student = new Student();
        student.setParent(parent);
        student.setFullName("Jane Doe");
        student.setClassLevel("GRADE_1");
        student.setIsActive(true);
        student.setEnrollmentDate(LocalDate.now());
        student.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateStudent() {
        assertThat(student).isNotNull();
        assertThat(student.getFullName()).isEqualTo("Jane Doe");
        assertThat(student.getClassLevel()).isEqualTo("GRADE_1");
        assertThat(student.getParent().getName()).isEqualTo("John Doe");
    }

    @Test
    void shouldUpdateStudentClass() {
        student.setClassLevel("GRADE_2");
        
        assertThat(student.getClassLevel()).isEqualTo("GRADE_2");
    }
}