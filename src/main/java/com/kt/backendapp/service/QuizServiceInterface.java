package com.kt.backendapp.service;

import com.kt.backendapp.dto.quiz.QuizCreateDto;
import com.kt.backendapp.dto.quiz.QuizResponseDto;
import com.kt.backendapp.dto.quiz.QuizUpdateDto;

import java.util.List;

public interface QuizServiceInterface {
    
    // 퀴즈 생성
    QuizResponseDto createQuiz(QuizCreateDto createDto);
    
    // 모든 퀴즈 목록 조회
    List<QuizResponseDto> getAllQuizzes();
    
    // ID로 퀴즈 조회
    QuizResponseDto getQuizById(Long id);
    
    // 교사별 퀴즈 목록 조회
    List<QuizResponseDto> getQuizzesByTeacher(Long teacherId);
    
    // 활성 퀴즈 목록 조회
    List<QuizResponseDto> getActiveQuizzes();
    
    // 퀴즈 수정
    QuizResponseDto updateQuiz(Long id, QuizUpdateDto updateDto);
    
    // 퀴즈 삭제
    void deleteQuiz(Long id);
} 