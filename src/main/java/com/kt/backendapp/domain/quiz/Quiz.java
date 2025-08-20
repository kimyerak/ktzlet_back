package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz", indexes = {
        @Index(name = "idx_quiz_created_by", columnList = "created_by")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Quiz extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(name = "numofquestion")
    private Integer numOfQuestions;

    @Column(name = "open_at")
    private LocalDateTime openAt;

    @Column(name = "close_at")
    private LocalDateTime closeAt;

    @Column(name = "time_limit_sec")
    private Integer timeLimitSec;

    @Column(name = "target_score")
    private Integer targetScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy; // teacher FK
}
