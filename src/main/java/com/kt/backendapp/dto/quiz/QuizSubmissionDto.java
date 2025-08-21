package com.kt.backendapp.dto.quiz;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizSubmissionDto {
    private Long studentId;
    private List<AnswerDto> answers;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerDto {
        private Long questionId;
        private String answer;
    }
} 