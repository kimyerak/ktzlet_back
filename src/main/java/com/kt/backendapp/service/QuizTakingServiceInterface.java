package com.kt.backendapp.service;

import com.kt.backendapp.dto.quiztaking.QuizTakingResponseDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerResponseDto;

import java.util.List;

public interface QuizTakingServiceInterface {
    
    // 학생이 응시 가능한 퀴즈 목록 조회
    List<QuizTakingResponseDto> getAvailableQuizzes(Long studentId);
    
    // 퀴즈 응시 시작
    QuizTakingResponseDto startQuiz(Long quizId, Long studentId);
    
    // 문제별 답안 제출
    QuestionAnswerResponseDto submitAnswer(QuestionAnswerDto answerDto);
    
    // 퀴즈 제출 완료
    QuizTakingResponseDto submitQuiz(Long quizId, Long studentId);
    
    // 학생의 퀴즈 응시 결과 조회
    List<QuizTakingResponseDto> getQuizResults(Long studentId);
    
    // 특정 퀴즈 응시 결과 조회
    QuizTakingResponseDto getQuizResult(Long quizId, Long studentId);
} 