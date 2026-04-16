package com.school.accounting.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
        
        Optional<SchoolUser> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        SchoolUser schoolUser = userOptional.get();
        
        String role = schoolUser.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        List<SimpleGrantedAuthority> authorities = List.of(authority); // This list holds the list of roles each user will have

        return new User(schoolUser.getUsername(), schoolUser.getPassword(), authorities);
    }
}