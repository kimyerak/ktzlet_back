package com.kt.backendapp.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Student {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(length = 10)
    private String level; // 학생 레벨 (예: beginner, intermediate, advanced)
} 