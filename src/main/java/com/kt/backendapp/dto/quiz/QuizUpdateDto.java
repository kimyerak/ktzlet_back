package com.kt.backendapp.dto.quiz;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizUpdateDto {
    
    @Size(max = 200, message = "제목은 200자 이하여야 합니다")
    private String title;
    
    private Integer numOfQuestions;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private Integer timeLimitSec;
    private Integer targetScore;
    
    // 문제 목록 (선택적)
    private List<QuestionUpdateDto> questions;
    
    // 단어장 ID 목록 (선택적)
    private List<Long> vocabIds;
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class QuestionUpdateDto {
        private Long id; // 기존 문제 수정 시
        private String type;
        private String stem;
        private String correctAnswer;
        private String explanation;
        private Integer points;
        private Long vocabId;
    }
} 