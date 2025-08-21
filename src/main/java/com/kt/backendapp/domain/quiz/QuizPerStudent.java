package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.user.Student;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "quiz_per_student",
    indexes = {
        @Index(name = "idx_qps_quiz", columnList = "quiz_id"),
        @Index(name = "idx_qps_student", columnList = "student_id"),
        @Index(name = "idx_qps_status", columnList = "status")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizPerStudent extends BaseTimeEntity {

    @EmbeddedId
    private QuizPerStudentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(nullable = false)
    private Boolean pass = false; // 기본값 false

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private QuizPerStudentStatus status; // assigned, in_progress, submitted, late

    // === 복합키 클래스 ===
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class QuizPerStudentId implements Serializable {
        private Long quizId;
        private Long studentId;
    }
}
