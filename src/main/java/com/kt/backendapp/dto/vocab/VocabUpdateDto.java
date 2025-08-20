package com.kt.backendapp.dto.vocab;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VocabUpdateDto {
    
    @Size(max = 100, message = "영단어는 100자 이하여야 합니다")
    private String word; // 영단어
    
    @Size(max = 100, message = "한국어 뜻은 100자 이하여야 합니다")
    private String definition; // 한국어 뜻
} 