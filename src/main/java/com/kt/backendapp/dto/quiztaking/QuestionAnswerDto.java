package com.kt.backendapp.dto.quiztaking;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuestionAnswerDto {
    
    @NotNull(message = "문제 ID는 필수입니다")
    private Long questionId;
    
    @NotNull(message = "학생 ID는 필수입니다")
    private Long studentId;
    
    @NotNull(message = "답안은 필수입니다")
    private String answer; // 학생이 제출한 답안
} 