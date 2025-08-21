package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.quiz.QuizPerStudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizPerStudentRepository extends JpaRepository<QuizPerStudent, QuizPerStudent.QuizPerStudentId> {
    
    // 학생별 퀴즈 응시 목록 조회
    List<QuizPerStudent> findById_StudentId(Long studentId);
    
    // 퀴즈별 응시 학생 목록 조회
    List<QuizPerStudent> findById_QuizId(Long quizId);
    
    
    // 특정 학생의 특정 퀴즈 응시 기록 조회
    Optional<QuizPerStudent> findById_QuizIdAndId_StudentId(Long quizId, Long studentId);
    
    // 학생별 응시 현황 조회 (상태별)
    List<QuizPerStudent> findById_StudentIdAndStatus(Long studentId, QuizPerStudentStatus status);
    
    // 퀴즈별 응시 현황 조회 (상태별)
    List<QuizPerStudent> findById_QuizIdAndStatus(Long quizId, QuizPerStudentStatus status);
    
    // 학생별 합격한 퀴즈 목록 조회
    @Query("SELECT qps FROM QuizPerStudent qps WHERE qps.id.studentId = :studentId AND qps.pass = true")
    List<QuizPerStudent> findPassedQuizzesByStudentId(@Param("studentId") Long studentId);
    
    // 학생별 평균 점수 조회
    @Query("SELECT AVG(qps.totalScore) FROM QuizPerStudent qps WHERE qps.id.studentId = :studentId AND qps.totalScore IS NOT NULL")
    Double getAverageScoreByStudentId(@Param("studentId") Long studentId);
    
    // 퀴즈별 평균 점수 조회
    @Query("SELECT AVG(qps.totalScore) FROM QuizPerStudent qps WHERE qps.id.quizId = :quizId AND qps.totalScore IS NOT NULL")
    Double getAverageScoreByQuizId(@Param("quizId") Long quizId);
    
    // 학생별 최근 응시 기록 조회
    @Query("SELECT qps FROM QuizPerStudent qps WHERE qps.id.studentId = :studentId ORDER BY qps.createdAt DESC")
    List<QuizPerStudent> findRecentQuizzesByStudentId(@Param("studentId") Long studentId);
    
    // 기간별 학생 응시 기록 조회
    @Query("SELECT qps FROM QuizPerStudent qps WHERE qps.id.studentId = :studentId AND qps.createdAt BETWEEN :startDate AND :endDate")
    List<QuizPerStudent> findByStudentIdAndDateRange(
            @Param("studentId") Long studentId, 
            @Param("startDate") LocalDateTime startDate, 
            @Param("endDate") LocalDateTime endDate);
} 