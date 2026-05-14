package com.school.accounting.service;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.school.accounting.model.SchoolUser;

class CustomUserDetailsServiceTest {

    private SchoolUser activeUser;
    private SchoolUser inactiveUser;

    @BeforeEach
    void setUp() {
        activeUser = new SchoolUser();
        activeUser.setEmail("active@marvangel.com");
        activeUser.setPassword("hashedPassword");
        activeUser.setFullName("Active User");
        activeUser.setRole("ACCOUNTANT");
        activeUser.setIsActive(true);
        activeUser.setCreatedAt(LocalDateTime.now());

        inactiveUser = new SchoolUser();
        inactiveUser.setEmail("inactive@marvangel.com");
        inactiveUser.setPassword("hashedPassword");
        inactiveUser.setFullName("Inactive User");
        inactiveUser.setRole("AUDITOR");
        inactiveUser.setIsActive(false);
        inactiveUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void activeUserShouldBeActive() {
        assertThat(activeUser.getIsActive()).isTrue();
        assertThat(activeUser.getRole()).isEqualTo("ACCOUNTANT");
    }

    @Test
    void inactiveUserShouldNotBeActive() {
        assertThat(inactiveUser.getIsActive()).isFalse();
        assertThat(inactiveUser.getRole()).isEqualTo("AUDITOR");
    }
}