package com.kt.backendapp.dto.question;

import com.kt.backendapp.domain.question.QuestionType;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuestionUpdateDto {
    
    private QuestionType type;
    private String stem;
    private String correctAnswer;
    private String explanation;
    private Integer points;
    private Long vocabId;
} 