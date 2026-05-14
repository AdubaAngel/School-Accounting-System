package com.school.accounting.repository;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.school.accounting.model.Payment;
import com.school.accounting.model.Student;

class PaymentRepositoryTest {

    private Payment testPayment;
    private Student testStudent;

    @BeforeEach
    void setUp() {
        // Create a test student
        testStudent = new Student();
        testStudent.setId(1L);
        testStudent.setFullName("Test Student");
        testStudent.setClassLevel("GRADE_1");
        testStudent.setIsActive(true);

        // Create a test payment
        testPayment = new Payment();
        testPayment.setStudent(testStudent);
        testPayment.setAmountPaid(new BigDecimal("35000.00"));
        testPayment.setPaymentDate(LocalDate.now());
        testPayment.setBankAccount("ACCOUNT1");
        testPayment.setTerm("TERM1");
        testPayment.setAcademicYear("2024-2025");
    }

    @Test
    void shouldCreatePayment() {
        assertThat(testPayment).isNotNull();
        assertThat(testPayment.getAmountPaid()).isEqualTo(new BigDecimal("35000.00"));
        assertThat(testPayment.getBankAccount()).isEqualTo("ACCOUNT1");
        assertThat(testPayment.getTerm()).isEqualTo("TERM1");
    }

    @Test
    void shouldSetPaymentFields() {
        testPayment.setReceiptNumber("2024-TERM1-0001");
        testPayment.setNotes("Partial payment");
        
        assertThat(testPayment.getReceiptNumber()).isEqualTo("2024-TERM1-0001");
        assertThat(testPayment.getNotes()).isEqualTo("Partial payment");
    }

    @Test
    void shouldLinkPaymentToStudent() {
        assertThat(testPayment.getStudent()).isNotNull();
        assertThat(testPayment.getStudent().getFullName()).isEqualTo("Test Student");
        assertThat(testPayment.getStudent().getClassLevel()).isEqualTo("GRADE_1");
    }
}