package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.user.Teacher;
import com.kt.backendapp.domain.question.Question;
import com.kt.backendapp.domain.vocab.Vocab;  
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

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
    private Teacher createdBy; // teacher FK

    // ✅ 문제 리스트 (1:N)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    // ✅ 단어장 리스트 (N:M)
    @ManyToMany
    @JoinTable(
        name = "quiz_vocab",
        joinColumns = @JoinColumn(name = "quiz_id"),
        inverseJoinColumns = @JoinColumn(name = "vocab_id")
    )
    private List<Vocab> vocabs = new ArrayList<>();
}
