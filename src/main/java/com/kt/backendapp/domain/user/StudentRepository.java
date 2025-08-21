package com.kt.backendapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // User ID로 Student 조회
    Optional<Student> findByUserId(Long userId);
    
    // 레벨별 학생 조회
    List<Student> findByLevel(String level);
} 