package com.kt.backendapp.domain.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResponsePerQuestionRepository extends JpaRepository<ResponsePerQuestion, Long> {
    
    // 학생별 답안 목록 조회
    List<ResponsePerQuestion> findByStudentId(Long studentId);
    
    // 특정 퀴즈의 학생 답안 목록 조회
    @Query("SELECT rpq FROM ResponsePerQuestion rpq WHERE rpq.student.id = :studentId AND rpq.question.quiz.id = :quizId")
    List<ResponsePerQuestion> findByStudentIdAndQuizId(@Param("studentId") Long studentId, @Param("quizId") Long quizId);
    
    // 특정 문제의 학생 답안 조회
    List<ResponsePerQuestion> findByQuestionId(Long questionId);
    
    // 학생의 특정 문제 답안 조회
    Optional<ResponsePerQuestion> findByStudentIdAndQuestionId(Long studentId, Long questionId);
    
    // 정답/오답별 답안 목록 조회
    List<ResponsePerQuestion> findByStudentIdAndIsCorrect(Long studentId, Boolean isCorrect);
} 