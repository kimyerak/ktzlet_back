package com.kt.backendapp.dto.quiz;

import com.kt.backendapp.domain.quiz.Quiz;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizResponseDto {
    
    private Long id;
    private String title;
    private Integer numOfQuestions;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private Integer timeLimitSec;
    private Integer targetScore;
    private Long createdBy;
    private String creatorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 문제 목록
    private List<QuestionResponseDto> questions;
    
    // 단어장 목록
    private List<VocabResponseDto> vocabs;
    
    public static QuizResponseDto from(Quiz quiz) {
        QuizResponseDto.QuizResponseDtoBuilder builder = QuizResponseDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .numOfQuestions(quiz.getNumOfQuestions())
                .openAt(quiz.getOpenAt())
                .closeAt(quiz.getCloseAt())
                .timeLimitSec(quiz.getTimeLimitSec())
                .targetScore(quiz.getTargetScore())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt());
        
        // LAZY 로딩 문제 해결
        if (quiz.getCreatedBy() != null) {
            builder.createdBy(quiz.getCreatedBy().getId())
                   .creatorName(quiz.getCreatedBy().getName());
        }
        
        return builder.build();
    }
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class QuestionResponseDto {
        private Long id;
        private String type;
        private String stem;
        private String correctAnswer;
        private String explanation;
        private Integer points;
        private Long vocabId;
    }
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class VocabResponseDto {
        private Long id;
        private String word;
        private String language;
    }
} 