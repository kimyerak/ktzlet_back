package com.kt.backendapp.domain.question;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.quiz.Quiz;
import com.kt.backendapp.domain.vocab.Vocab;
import com.kt.backendapp.domain.question.QuestionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question", indexes = {
        @Index(name = "idx_question_quiz_id", columnList = "quiz_id"),
        @Index(name = "uk_question_quiz_vocab", columnList = "quiz_id, vocab_id", unique = true)
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Question extends BaseTimeEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocab_id")
    private Vocab vocab;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false) // OX, DICTATION, MULTIPLE 등
    private QuestionType type;
    
    @Column(columnDefinition = "text", nullable = false)
    private String stem;
    
    @Column(name = "correct_answer", columnDefinition = "text", nullable = false)
    private String correctAnswer;
    
    @Column(columnDefinition = "text")
    private String explanation;
    
    private Integer points;
}
