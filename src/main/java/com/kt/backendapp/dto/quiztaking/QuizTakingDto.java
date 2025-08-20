package com.kt.backendapp.dto.quiztaking;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizTakingDto {
    
    @NotNull(message = "답안 목록은 필수입니다")
    private List<AnswerDto> answers;
    
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class AnswerDto {
        @NotNull(message = "문제 ID는 필수입니다")
        private Long questionId;
        
        private String answer; // 학생이 제출한 답안
    }
} 