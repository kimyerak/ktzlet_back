package com.kt.backendapp.controller;

import com.kt.backendapp.domain.question.QuestionType;
import com.kt.backendapp.dto.question.QuestionCreateDto;
import com.kt.backendapp.dto.question.QuestionResponseDto;
import com.kt.backendapp.dto.question.QuestionUpdateDto;
import com.kt.backendapp.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionService questionService;
    
    // 문제 생성
    @PostMapping
    public ResponseEntity<QuestionResponseDto> createQuestion(
            @Valid @RequestBody QuestionCreateDto createDto) {
        QuestionResponseDto responseDto = questionService.createQuestion(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    // 퀴즈별 문제 목록 조회
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByQuizId(@PathVariable Long quizId) {
        List<QuestionResponseDto> questions = questionService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }
    
    // 문제 상세 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long questionId) {
        QuestionResponseDto question = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(question);
    }
    
    // 문제 수정
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> updateQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionUpdateDto updateDto) {
        QuestionResponseDto responseDto = questionService.updateQuestion(questionId, updateDto);
        return ResponseEntity.ok(responseDto);
    }
    
    // 문제 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
    
    // 문제 유형별 조회
    @GetMapping("/quiz/{quizId}/type/{type}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByQuizIdAndType(
            @PathVariable Long quizId,
            @PathVariable QuestionType type) {
        List<QuestionResponseDto> questions = questionService.getQuestionsByQuizIdAndType(quizId, type);
        return ResponseEntity.ok(questions);
    }
    
    // 단어장별 문제 목록 조회 (받아쓰기 문제)
    @GetMapping("/vocab/{vocabId}")
    public ResponseEntity<List<QuestionResponseDto>> getQuestionsByVocabId(@PathVariable Long vocabId) {
        List<QuestionResponseDto> questions = questionService.getQuestionsByVocabId(vocabId);
        return ResponseEntity.ok(questions);
    }
} 