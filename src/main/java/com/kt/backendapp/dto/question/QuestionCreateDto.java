package com.kt.backendapp.dto.question;

import java.util.List;

import com.kt.backendapp.domain.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuestionCreateDto {
    
    @NotNull(message = "퀴즈 ID는 필수입니다")
    private Long quizId;
    
    @NotNull(message = "문제 유형은 필수입니다")
    private QuestionType type;
    
    @NotBlank(message = "문제 내용은 필수입니다")
    private String stem;
    
    @NotBlank(message = "정답은 필수입니다")
    private String correctAnswer;
    
    private String explanation;
    
    private Integer points;
    
    private Long vocabId; 
    // ✅ 객관식일 경우만 사용
    private List<String> options; 
} 