package com.school.accounting.model;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParentTest {

    private Parent parent;

    @BeforeEach
    void setUp() {
        parent = new Parent();
        parent.setName("John Doe");
        parent.setEmail("john@example.com");
        parent.setPhone("08012345678");
        parent.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateParent() {
        assertThat(parent).isNotNull();
        assertThat(parent.getName()).isEqualTo("John Doe");
        assertThat(parent.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void shouldUpdateParentInfo() {
        parent.setName("Jane Doe");
        parent.setPhone("08098765432");
        
        assertThat(parent.getName()).isEqualTo("Jane Doe");
        assertThat(parent.getPhone()).isEqualTo("08098765432");
    }
}