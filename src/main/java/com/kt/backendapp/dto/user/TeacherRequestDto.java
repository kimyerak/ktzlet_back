package com.kt.backendapp.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TeacherRequestDto {
    
    @Valid
    @NotNull(message = "사용자 정보는 필수입니다")
    private UserRequestDto userInfo;
    
    // Teacher 특화 필드들이 있다면 여기에 추가
    // 예: subject, department 등
} 