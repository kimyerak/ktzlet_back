package com.kt.backendapp.dto.question;

import com.kt.backendapp.domain.question.Question;
import com.kt.backendapp.domain.question.QuestionType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuestionResponseDto {

    private Long id;
    private Long quizId;
    private QuestionType type;
    private String stem;
    private String correctAnswer;
    private String explanation;
    private Integer points;
    private Long vocabId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ 추가
    private List<String> options;

    public static QuestionResponseDto from(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .quizId(question.getQuiz().getId())
                .type(question.getType())
                .stem(question.getStem())
                .correctAnswer(question.getCorrectAnswer())
                .explanation(question.getExplanation())
                .points(question.getPoints())
                .vocabId(question.getVocab() != null ? question.getVocab().getId() : null)
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                // ✅ 추가
                .options(question.getOptions())
                .build();
    }
}
