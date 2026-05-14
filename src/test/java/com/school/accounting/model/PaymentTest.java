package com.school.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentTest {

    private Payment payment;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFullName("Jane Doe");
        
        payment = new Payment();
        payment.setStudent(student);
        payment.setAmountPaid(new BigDecimal("35000.00"));
        payment.setPaymentDate(LocalDate.now());
        payment.setBankAccount("ACCOUNT1");
        payment.setTerm("TERM1");
        payment.setAcademicYear("2024-2025");
    }

    @Test
    void shouldCreatePayment() {
        assertThat(payment).isNotNull();
        assertThat(payment.getAmountPaid()).isEqualTo(new BigDecimal("35000.00"));
        assertThat(payment.getBankAccount()).isEqualTo("ACCOUNT1");
        assertThat(payment.getStudent().getFullName()).isEqualTo("Jane Doe");
    }

    @Test
    void shouldUpdatePaymentWithReceiptNumber() {
        payment.setReceiptNumber("2024-TERM1-0001");
        
        assertThat(payment.getReceiptNumber()).isEqualTo("2024-TERM1-0001");
    }
}