package com.kt.backendapp.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teacher")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Teacher {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // Teacher 특화 필드들이 있다면 여기에 추가
    // 예: subject, department 등
} 