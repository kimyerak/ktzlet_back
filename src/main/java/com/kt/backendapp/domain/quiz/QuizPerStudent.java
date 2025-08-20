package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_per_student")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class QuizPerStudent extends BaseTimeEntity {
    
    @EmbeddedId
    private QuizPerStudentId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private User student;
    
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
    
    @Column(name = "total_score")
    private Integer totalScore;
    
    private Boolean pass;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private QuizPerStudentStatus status; // assigned, in_progress, submitted, late
    
    @Embeddable
    @Getter @Setter
    @NoArgsConstructor @AllArgsConstructor
    @EqualsAndHashCode
    public static class QuizPerStudentId implements java.io.Serializable {
        private Long quizId;
        private Long studentId;
    }
}
