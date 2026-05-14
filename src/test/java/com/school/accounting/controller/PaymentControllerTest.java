package com.school.accounting.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.school.accounting.model.Payment;
import com.school.accounting.model.Student;

class PaymentControllerTest {

    private Payment payment;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFullName("Test Student");

        payment = new Payment();
        payment.setStudent(student);
        payment.setAmountPaid(new BigDecimal("35000.00"));
        payment.setPaymentDate(LocalDate.now());
        payment.setBankAccount("ACCOUNT1");
        payment.setTerm("TERM1");
        payment.setAcademicYear("2024-2025");
    }

    @Test
    void shouldCreateValidPayment() {
        assertThat(payment.getAmountPaid()).isGreaterThan(BigDecimal.ZERO);
        assertThat(payment.getBankAccount()).isIn("ACCOUNT1", "ACCOUNT2", "ACCOUNT3");
        assertThat(payment.getTerm()).isIn("TERM1", "TERM2", "TERM3");
    }

    @Test
    void shouldHaveValidPaymentDate() {
        assertThat(payment.getPaymentDate()).isNotNull();
        assertThat(payment.getPaymentDate()).isBeforeOrEqualTo(LocalDate.now());
    }
}