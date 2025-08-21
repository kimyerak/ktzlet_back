package com.kt.backendapp.dto.question;

import com.kt.backendapp.domain.question.QuestionType;
import lombok.*;

import java.util.List;

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

    // ✅ 객관식 문제의 보기를 위한 필드 추가
    private List<String> options;
}
