package com.kt.backendapp.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentRequestDto {
    
    @Valid
    @NotNull(message = "사용자 정보는 필수입니다")
    private UserRequestDto userInfo;
    
    private String level; // 학생 레벨 (예: beginner, intermediate, advanced)
} 