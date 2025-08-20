package com.kt.backendapp.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    // 퀴즈별 문제 목록 조회
    List<Question> findByQuizId(Long quizId);
    
    // 문제 유형별 조회
    List<Question> findByQuizIdAndType(Long quizId, QuestionType type);
    
    // 단어장별 문제 목록 조회 (받아쓰기 문제)
    @Query("SELECT q FROM Question q WHERE q.vocab.id = :vocabId")
    List<Question> findByVocabId(@Param("vocabId") Long vocabId);
    
    // 퀴즈별 문제 개수 조회
    @Query("SELECT COUNT(q) FROM Question q WHERE q.quiz.id = :quizId")
    Long countByQuizId(@Param("quizId") Long quizId);
    
    // 문제 유형별 개수 조회
    @Query("SELECT COUNT(q) FROM Question q WHERE q.quiz.id = :quizId AND q.type = :type")
    Long countByQuizIdAndType(@Param("quizId") Long quizId, @Param("type") QuestionType type);
} 