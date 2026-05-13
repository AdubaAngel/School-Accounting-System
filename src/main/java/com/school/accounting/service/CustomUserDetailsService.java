package com.school.accounting.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.accounting.model.SchoolUser;
import com.school.accounting.repository.SchoolUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SchoolUserRepository userRepository;

    public CustomUserDetailsService(SchoolUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Searching for email: '" + email + "'");
        
        SchoolUser user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("User found: " + user.getFullName());
        System.out.println("Is active: " + user.getIsActive());
        System.out.println("Role: " + user.getRole());

        if (!user.getIsActive()) {
            System.out.println("User is INACTIVE - denying login");
            throw new UsernameNotFoundException("User account is deactivated: " + email);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        List<SimpleGrantedAuthority> authorities = List.of(authority);

        System.out.println("Login successful for: " + user.getFullName());
        
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
}