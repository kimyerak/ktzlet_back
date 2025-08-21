package com.kt.backendapp.dto.quiztaking;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuestionAnswerResponseDto {
    
    private Long questionId;
    private Long studentId;
    private String questionStem;
    private String submittedAnswer;
    private String correctAnswer;
    private Boolean isCorrect;
    private Integer points;
    private String explanation;
    private String retryAnswer;
    private String retryResult;
    private Integer retryCount;
    private LocalDateTime submittedAt;
    
    // 재시도 가능 여부
    private Boolean canRetry;
    
    // 다음 문제 ID (선택적)
    private Long nextQuestionId;
} 