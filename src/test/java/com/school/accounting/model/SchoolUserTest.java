package com.school.accounting.model;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SchoolUserTest {

    private SchoolUser user;

    @BeforeEach
    void setUp() {
        user = new SchoolUser();
        user.setUsername("testuser");
        user.setEmail("testuser@marvangel.com");
        user.setFullName("Test User");
        user.setRole("ACCOUNTANT");
        user.setPassword("password123");
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateSchoolUser() {
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getEmail()).isEqualTo("testuser@marvangel.com");
    }

    @Test
    void shouldUpdateUserFields() {
        user.setFullName("Updated Name");
        user.setRole("AUDITOR");
        
        assertThat(user.getFullName()).isEqualTo("Updated Name");
        assertThat(user.getRole()).isEqualTo("AUDITOR");
    }

    @Test
    void shouldDeactivateUser() {
        user.setIsActive(false);
        
        assertThat(user.getIsActive()).isFalse();
    }
}