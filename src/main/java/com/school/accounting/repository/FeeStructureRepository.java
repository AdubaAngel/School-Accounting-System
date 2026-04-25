package com.school.accounting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.school.accounting.model.FeeStructure;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    
    List<FeeStructure> findByAcademicYear(String academicYear);
    
    List<FeeStructure> findByClassLevel(String classLevel);
    
    Optional<FeeStructure> findByClassLevelAndTermAndAcademicYear(
        String classLevel, String term, String academicYear);
}
