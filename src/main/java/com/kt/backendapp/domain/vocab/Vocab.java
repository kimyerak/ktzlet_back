package com.kt.backendapp.domain.vocab;

import java.util.ArrayList;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.quiz.Quiz;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;        // ✅ 추가
import java.util.ArrayList;  // ✅ 추가

@Entity
@Table(name = "vocab")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Vocab extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String word; // 영단어
    
    @Column(length = 100)
    private String definition; // 한국어 뜻

    // ✅ 역방향 매핑
    @ManyToMany(mappedBy = "vocabs")
    private List<Quiz> quizzes = new ArrayList<>();
}
