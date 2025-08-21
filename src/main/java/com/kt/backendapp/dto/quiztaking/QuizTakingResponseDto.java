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
    
    // 응시 가능한 퀴즈 목록을 위한 추가 필드들
    private Integer numOfQuestions;
    private LocalDateTime openAt;
    private LocalDateTime closeAt;
    private Integer timeLimitSec;
    private Integer targetScore;
    
    // 채점 결과 상세 정보
    private List<QuestionResultDto> questionResults;
    
    public static QuizTakingResponseDto from(QuizPerStudent quizPerStudent) {
        return QuizTakingResponseDto.builder()
                .quizId(quizPerStudent.getQuiz().getId())
                .quizTitle(quizPerStudent.getQuiz().getTitle())
                .studentId(quizPerStudent.getStudent().getId())
                .studentName(quizPerStudent.getStudent().getUser().getName()) // Student의 User 정보에서 이름 가져오기
                .startedAt(quizPerStudent.getStartedAt())
                .submittedAt(quizPerStudent.getSubmittedAt())
                .totalScore(quizPerStudent.getTotalScore())
                .pass(quizPerStudent.getPass())
                .status(quizPerStudent.getStatus())
                .createdAt(quizPerStudent.getCreatedAt())
                .updatedAt(quizPerStudent.getUpdatedAt())
                .build();
    }
    
    // 응시 가능한 퀴즈 목록을 위한 정적 메서드 추가
    public static QuizTakingResponseDto fromQuiz(com.kt.backendapp.domain.quiz.Quiz quiz, Long studentId, String studentName) {
        return QuizTakingResponseDto.builder()
                .quizId(quiz.getId())
                .quizTitle(quiz.getTitle())
                .studentId(studentId)
                .studentName(studentName)
                .numOfQuestions(quiz.getNumOfQuestions())
                .openAt(quiz.getOpenAt())
                .closeAt(quiz.getCloseAt())
                .timeLimitSec(quiz.getTimeLimitSec())
                .targetScore(quiz.getTargetScore())
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