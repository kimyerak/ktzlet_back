package com.kt.backendapp.domain.quiz;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import com.kt.backendapp.domain.question.Question;
import com.kt.backendapp.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "response_per_question")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ResponsePerQuestion extends BaseTimeEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student; // role=student
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    @Column(length = 30)
    private String response; // 학생이 제출한 답안
    
    @Column(name = "is_correct")
    private Boolean isCorrect; // 정답 여부
    
    @Column(name = "retry_answer", length = 30)
    private String retryAnswer; // 재시도 답안
    
    @Column(name = "retry_result", length = 30)
    private String retryResult; // 재시도 결과
    
    @Column(name = "retry_count")
    private Integer retryCount; // 재시도 횟수
} 