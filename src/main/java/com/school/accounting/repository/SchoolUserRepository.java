package com.school.accounting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.accounting.model.SchoolUser;

@Repository
public interface SchoolUserRepository extends JpaRepository<SchoolUser, Long> {
    Optional<SchoolUser> findByUsername(String username);
}