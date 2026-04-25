package com.school.accounting.service;

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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Searching for username: '" + username + "'");
        
        SchoolUser user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        System.out.println("User found: " + user.getUsername());
        System.out.println("Is active: " + user.getIsActive());
        System.out.println("Role: " + user.getRole());

        if (!user.getIsActive()) {
            System.out.println("User is INACTIVE - denying login");
            throw new UsernameNotFoundException("User account is deactivated: " + username);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        List<SimpleGrantedAuthority> authorities = List.of(authority);

        System.out.println("Login successful for: " + username);
        System.out.println("Returning UserDetails for: " + username);
        System.out.println("Password hash length: " + user.getPassword().length());
        
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }
}