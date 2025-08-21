package com.kt.backendapp.controller;

import com.kt.backendapp.dto.quiz.QuizCreateDto;
import com.kt.backendapp.dto.quiz.QuizResponseDto;
import com.kt.backendapp.dto.quiz.QuizUpdateDto;
import com.kt.backendapp.dto.quiztaking.QuizTakingResponseDto;
import com.kt.backendapp.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    
    private final QuizService quizService;
    
    // 퀴즈 생성
    @PostMapping
    public ResponseEntity<QuizResponseDto> createQuiz(
            @Valid @RequestBody QuizCreateDto createDto) {
        QuizResponseDto responseDto = quizService.createQuiz(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 퀴즈 전체 조회
@GetMapping
public ResponseEntity<List<QuizResponseDto>> getAllQuizzes() {
    List<QuizResponseDto> quizzes = quizService.getAllQuizzes();
    return ResponseEntity.ok(quizzes);
}



    
    // 퀴즈 목록 조회 (교사용)
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<QuizResponseDto>> getQuizzesByTeacher(@PathVariable Long teacherId) {
        List<QuizResponseDto> quizzes = quizService.getQuizzesByTeacher(teacherId);
        return ResponseEntity.ok(quizzes);
    }
    
    // 퀴즈 상세 조회
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> getQuizById(@PathVariable Long quizId) {
        QuizResponseDto quiz = quizService.getQuizById(quizId);
        return ResponseEntity.ok(quiz);
    }
    
    // 퀴즈 수정
    @PutMapping("/{quizId}")
    public ResponseEntity<QuizResponseDto> updateQuiz(
            @PathVariable Long quizId,
            @Valid @RequestBody QuizUpdateDto updateDto) {
        QuizResponseDto responseDto = quizService.updateQuiz(quizId, updateDto);
        return ResponseEntity.ok(responseDto);
    }
    
    // 퀴즈 삭제
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
    
    // 퀴즈 상태 변경 (공개/비공개)
    @PatchMapping("/{quizId}/status")
    public ResponseEntity<QuizResponseDto> updateQuizStatus(
            @PathVariable Long quizId,
            @RequestParam String status) {
        QuizResponseDto responseDto = quizService.updateQuizStatus(quizId, status);
        return ResponseEntity.ok(responseDto);
    }
} 