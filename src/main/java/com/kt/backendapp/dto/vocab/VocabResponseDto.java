package com.kt.backendapp.dto.vocab;

import com.kt.backendapp.domain.vocab.Vocab;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VocabResponseDto {
    
    private Long id;
    private String word; // 영단어
    private String definition; // 한국어 뜻
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static VocabResponseDto from(Vocab vocab) {
        return VocabResponseDto.builder()
                .id(vocab.getId())
                .word(vocab.getWord())
                .definition(vocab.getDefinition())
                .createdAt(vocab.getCreatedAt())
                .updatedAt(vocab.getUpdatedAt())
                .build();
    }
} 