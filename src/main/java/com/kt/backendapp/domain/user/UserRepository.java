package com.kt.backendapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);
    
    // 이메일 존재 여부 확인
    boolean existsByEmail(String email);
    
    // 역할별 사용자 목록 조회
    List<User> findByRole(UserRole role);
    
    // 상태별 사용자 목록 조회
    List<User> findByStatus(UserStatus status);
    
    // 역할과 상태로 사용자 목록 조회
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    
    // 이름으로 사용자 검색 (부분 일치)
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);
    
    // 활성 상태의 학생 목록 조회
    @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.status = 'ACTIVE'")
    List<User> findActiveStudents();
    
    // 활성 상태의 교사 목록 조회
    @Query("SELECT u FROM User u WHERE u.role = 'TEACHER' AND u.status = 'ACTIVE'")
    List<User> findActiveTeachers();
} 