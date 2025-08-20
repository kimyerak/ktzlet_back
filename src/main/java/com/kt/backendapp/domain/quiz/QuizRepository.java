package com.kt.backendapp.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
    // 교사별 퀴즈 목록 조회
    List<Quiz> findByCreatedById(Long teacherId);
    
    // 활성 퀴즈 목록 조회 (현재 시간 기준)
    @Query("SELECT q FROM Quiz q WHERE q.openAt <= :now AND q.closeAt >= :now")
    List<Quiz> findActiveQuizzes(@Param("now") LocalDateTime now);
    
    // 학생이 응시 가능한 퀴즈 목록 조회
    @Query("SELECT q FROM Quiz q WHERE q.openAt <= :now AND q.closeAt >= :now " +
           "AND q.id NOT IN (SELECT qps.quiz.id FROM QuizPerStudent qps WHERE qps.student.id = :studentId)")
    List<Quiz> findAvailableQuizzesForStudent(@Param("studentId") Long studentId, @Param("now") LocalDateTime now);
    
    // 제목으로 퀴즈 검색
    List<Quiz> findByTitleContainingIgnoreCase(String title);
    
    // 특정 기간 내 퀴즈 목록 조회
    @Query("SELECT q FROM Quiz q WHERE q.createdAt BETWEEN :startDate AND :endDate")
    List<Quiz> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
} 