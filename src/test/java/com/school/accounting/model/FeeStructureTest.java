package com.school.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeeStructureTest {

    private FeeStructure fee;

    @BeforeEach
    void setUp() {
        fee = new FeeStructure();
        fee.setClassLevel(ClassLevel.GRADE_1);
        fee.setTerm(Term.TERM1);
        fee.setAcademicYear("2024-2025");
        fee.setAmount(new BigDecimal("35000.00"));
        fee.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateFeeStructure() {
        assertThat(fee).isNotNull();
        assertThat(fee.getClassLevel()).isEqualTo(ClassLevel.GRADE_1);
        assertThat(fee.getTerm()).isEqualTo(Term.TERM1);
        assertThat(fee.getAmount()).isEqualTo(new BigDecimal("35000.00"));
    }

    @Test
    void shouldUpdateFeeAmount() {
        fee.setAmount(new BigDecimal("40000.00"));
        
        assertThat(fee.getAmount()).isEqualTo(new BigDecimal("40000.00"));
    }
}