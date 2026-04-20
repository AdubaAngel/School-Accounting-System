package com.school.accounting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.accounting.model.SchoolUser;
import com.school.accounting.repository.SchoolUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SchoolUserRepository userRepository;

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
    System.out.println("=== LOGIN ATTEMPT ===");
    System.out.println("Searching for username: '" + username + "'");
    
    Optional<SchoolUser> userOptional = userRepository.findByUsername(username);
    
    if (userOptional.isEmpty()) {
        System.out.println("RESULT: User NOT found in database");
        throw new UsernameNotFoundException("User not found: " + username);
    }
    
    SchoolUser schoolUser = userOptional.get();
    System.out.println("RESULT: User FOUND");
    System.out.println("  Username: " + schoolUser.getUsername());
    System.out.println("  Password from DB: '" + schoolUser.getPassword() + "'");
    System.out.println("  Role: " + schoolUser.getRole());
    
    String role = schoolUser.getRole();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
    List<SimpleGrantedAuthority> authorities = List.of(authority);
    
    System.out.println("  Authority created: " + authorities);
    System.out.println("=== RETURNING UserDetails ===");
    
    return new org.springframework.security.core.userdetails.User(
        schoolUser.getUsername(), 
        schoolUser.getPassword(), 
        authorities
    );
}
}