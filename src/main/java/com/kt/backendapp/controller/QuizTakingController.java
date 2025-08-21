package com.kt.backendapp.controller;

import com.kt.backendapp.dto.quiztaking.QuizTakingResponseDto;

import com.kt.backendapp.dto.quiztaking.QuestionAnswerDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerResponseDto;
import com.kt.backendapp.service.QuizTakingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-taking")
@RequiredArgsConstructor
public class QuizTakingController {
    
    private final QuizTakingService quizTakingService;
    
    // 응시 가능한 퀴즈 목록 조회
    @GetMapping("/available/{studentId}")
    public ResponseEntity<List<QuizTakingResponseDto>> getAvailableQuizzes(@PathVariable Long studentId) {
        List<QuizTakingResponseDto> quizzes = quizTakingService.getAvailableQuizzes(studentId);
        return ResponseEntity.ok(quizzes);
    }
    
    // 퀴즈 시작
    @PostMapping("/start/{quizId}/{studentId}")
    public ResponseEntity<QuizTakingResponseDto> startQuiz(
            @PathVariable Long quizId,
            @PathVariable Long studentId) {
        QuizTakingResponseDto response = quizTakingService.startQuiz(quizId, studentId);
        return ResponseEntity.ok(response);
    }
    
    // 문제별 답안 제출 (실시간 채점)
    @PostMapping("/question/{questionId}/answer")
    public ResponseEntity<QuestionAnswerResponseDto> submitQuestionAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionAnswerDto answerDto) {
        QuestionAnswerResponseDto response = quizTakingService.submitQuestionAnswer(questionId, answerDto);
        return ResponseEntity.ok(response);
    }
    
    // 퀴즈 완료 (최종 제출)
    @PostMapping("/complete/{quizId}/{studentId}")
    public ResponseEntity<QuizTakingResponseDto> completeQuiz(
            @PathVariable Long quizId,
            @PathVariable Long studentId) {
        QuizTakingResponseDto response = quizTakingService.completeQuiz(quizId, studentId);
        return ResponseEntity.ok(response);
    }
    
    // 학생 성적 히스토리 조회
    @GetMapping("/history/{studentId}")
    public ResponseEntity<List<QuizTakingResponseDto>> getStudentHistory(@PathVariable Long studentId) {
        List<QuizTakingResponseDto> history = quizTakingService.getStudentHistory(studentId);
        return ResponseEntity.ok(history);
    }
    
    // 특정 퀴즈 응시 결과 조회
    @GetMapping("/result/{quizId}/{studentId}")
    public ResponseEntity<QuizTakingResponseDto> getQuizResult(
            @PathVariable Long quizId,
            @PathVariable Long studentId) {
        QuizTakingResponseDto result = quizTakingService.getQuizResult(quizId, studentId);
        return ResponseEntity.ok(result);
    }
    // 이미 완료한 퀴즈만 조회
@GetMapping("/completed/{studentId}")
public ResponseEntity<List<QuizTakingResponseDto>> getCompletedQuizzes(@PathVariable Long studentId) {
    List<QuizTakingResponseDto> completed = quizTakingService.getCompletedQuizzes(studentId);
    return ResponseEntity.ok(completed);
}
} 