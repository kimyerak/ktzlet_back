package com.kt.backendapp.service;

import com.kt.backendapp.dto.question.QuestionCreateDto;
import com.kt.backendapp.dto.question.QuestionResponseDto;
import com.kt.backendapp.dto.question.QuestionUpdateDto;

import java.util.List;

public interface QuestionServiceInterface {
    
    // 문제 생성
    QuestionResponseDto createQuestion(QuestionCreateDto createDto);
    
    // 퀴즈별 문제 목록 조회
    List<QuestionResponseDto> getQuestionsByQuiz(Long quizId);
    
    // ID로 문제 조회
    QuestionResponseDto getQuestionById(Long id);
    
    // 문제 수정
    QuestionResponseDto updateQuestion(Long id, QuestionUpdateDto updateDto);
    
    // 문제 삭제
    void deleteQuestion(Long id);
} 