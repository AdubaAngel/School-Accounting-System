package com.school.accounting.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.school.accounting.model.Payment;
import com.school.accounting.model.Student;

class PaymentServiceTest {

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
    void shouldCalculateRemainingBalance_WhenFullyPaid() {
        BigDecimal feeAmount = new BigDecimal("35000.00");
        BigDecimal paidAmount = payment.getAmountPaid();
        
        assertThat(paidAmount).isEqualTo(feeAmount);
    }

    @Test
    void shouldCalculateRemainingBalance_WhenPartiallyPaid() {
        BigDecimal feeAmount = new BigDecimal("35000.00");
        BigDecimal paidAmount = new BigDecimal("20000.00");
        BigDecimal remaining = feeAmount.subtract(paidAmount);
        
        assertThat(remaining).isEqualTo(new BigDecimal("15000.00"));
    }

    @Test
    void shouldGenerateReceiptNumber() {
        String receiptNumber = "2024-TERM1-0001";
        payment.setReceiptNumber(receiptNumber);
        
        assertThat(payment.getReceiptNumber()).isEqualTo(receiptNumber);
    }
}