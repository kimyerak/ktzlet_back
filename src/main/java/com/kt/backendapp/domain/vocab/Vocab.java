package com.kt.backendapp.domain.vocab;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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
}
