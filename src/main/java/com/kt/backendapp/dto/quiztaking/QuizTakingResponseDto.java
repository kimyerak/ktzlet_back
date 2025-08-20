package com.kt.backendapp.dto.quiztaking;

import com.kt.backendapp.domain.quiz.QuizPerStudent;
import com.kt.backendapp.domain.quiz.QuizPerStudentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizTakingResponseDto {
    
    private Long quizId;
    private Long studentId;
    private String quizTitle;
    private String studentName;
    private LocalDateTime startedAt;
    private LocalDateTime submittedAt;
    private Integer totalScore;
    private Boolean pass;
    private QuizPerStudentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 채점 결과 상세 정보
    private List<QuestionResultDto> questionResults;
    
    public static QuizTakingResponseDto from(QuizPerStudent quizPerStudent) {
        return QuizTakingResponseDto.builder()
                .quizId(quizPerStudent.getQuiz().getId())
                .quizTitle(quizPerStudent.getQuiz().getTitle())
                .studentId(quizPerStudent.getStudent().getId())
                .studentName(quizPerStudent.getStudent().getName())
                .startedAt(quizPerStudent.getStartedAt())
                .submittedAt(quizPerStudent.getSubmittedAt())
                .totalScore(quizPerStudent.getTotalScore())
                .pass(quizPerStudent.getPass())
                .status(quizPerStudent.getStatus())
                .createdAt(quizPerStudent.getCreatedAt())
                .updatedAt(quizPerStudent.getUpdatedAt())
                .build();
    }
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class QuestionResultDto {
        private Long questionId;
        private String questionType;
        private String stem;
        private String studentAnswer;
        private String correctAnswer;
        private Boolean isCorrect;
        private Integer earnedPoints;
        private Integer maxPoints;
        private String explanation;
    }
} 