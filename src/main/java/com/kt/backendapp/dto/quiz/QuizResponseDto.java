package com.kt.backendapp.dto.quiz;

import com.kt.backendapp.domain.question.QuestionType;
import com.kt.backendapp.domain.quiz.Quiz;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.kt.backendapp.dto.question.QuestionResponseDto; // ✅ 외부 DTO import

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

        // teacher 정보
        if (quiz.getCreatedBy() != null && quiz.getCreatedBy().getUser() != null) {
            builder.createdBy(quiz.getCreatedBy().getId())
                   .creatorName(quiz.getCreatedBy().getUser().getName());
        }

        // ✅ questions 매핑 - 외부 QuestionResponseDto 사용
        if (quiz.getQuestions() != null) {
            builder.questions(
                quiz.getQuestions().stream()
                        .map(QuestionResponseDto::from) // 👈 핵심
                        .toList()
            );
        }

        // vocabs 매핑
        if (quiz.getVocabs() != null) {
            builder.vocabs(
                quiz.getVocabs().stream()
                        .map(v -> VocabResponseDto.builder()
                                .id(v.getId())
                                .word(v.getWord())
                                .definition(v.getDefinition())
                                .build())
                        .toList()
            );
        }

        return builder.build();
    }

    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class VocabResponseDto {
        private Long id;
        private String word;
        private String definition;
    }
}
