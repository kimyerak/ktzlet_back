package com.kt.backendapp.dto.quiz;

import com.kt.backendapp.domain.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizCreateDto {
    
    @NotBlank(message = "퀴즈 제목은 필수입니다")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;
    
    @NotNull(message = "문제 개수는 필수입니다")
    private Integer numOfQuestions;
    
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private Integer timeLimitSec;
    private Integer targetScore;
    
    @NotNull(message = "생성자 ID는 필수입니다")
    private Long createdBy;
    
    // 문제 목록
    private List<QuestionCreateDto> questions;
    
    // 단어장 ID 목록
    private List<Long> vocabIds;
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class QuestionCreateDto {
        @NotNull(message = "문제 유형은 필수입니다")
        private QuestionType type;
        
        @NotBlank(message = "문제 내용은 필수입니다")
        private String stem;
        
        @NotBlank(message = "정답은 필수입니다")
        private String correctAnswer;
        
        private String explanation;
        private Integer points;
        private Long vocabId; // 받아쓰기 문제의 경우
    }
} 