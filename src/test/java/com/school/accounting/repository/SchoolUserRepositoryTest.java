package com.school.accounting.repository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.school.accounting.model.SchoolUser;

class SchoolUserRepositoryTest {

    private SchoolUserRepository userRepository;
    private SchoolUser testUser;

    @BeforeEach
    void setUp() {
        // This is a simplified test - in real tests you would use a test database
        testUser = new SchoolUser();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@marvangel.com");
        testUser.setFullName("Test User");
        testUser.setRole("ACCOUNTANT");
        testUser.setPassword("hashedPassword123");
        testUser.setIsActive(true);
        testUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void shouldCreateUser() {
        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo("testuser@marvangel.com");
        assertThat(testUser.getFullName()).isEqualTo("Test User");
        assertThat(testUser.getRole()).isEqualTo("ACCOUNTANT");
    }

    @Test
    void shouldHaveCorrectUserFields() {
        assertThat(testUser.getUsername()).isEqualTo("testuser");
        assertThat(testUser.getIsActive()).isTrue();
        assertThat(testUser.getPassword()).isEqualTo("hashedPassword123");
    }
}