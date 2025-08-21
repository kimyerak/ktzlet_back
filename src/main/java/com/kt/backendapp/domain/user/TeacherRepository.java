package com.kt.backendapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    // User ID로 Teacher 조회
    Optional<Teacher> findByUserId(Long userId);
} 