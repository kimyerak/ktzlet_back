package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.Teacher;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TeacherResponseDto {
    
    private Long id;
    private UserResponseDto userInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Entity를 DTO로 변환하는 정적 메서드
    public static TeacherResponseDto from(Teacher teacher) {
        return TeacherResponseDto.builder()
                .id(teacher.getId())
                .userInfo(UserResponseDto.from(teacher.getUser()))
                .createdAt(teacher.getUser().getCreatedAt())
                .updatedAt(teacher.getUser().getUpdatedAt())
                .build();
    }
} 