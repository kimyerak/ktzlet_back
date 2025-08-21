package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.Student;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentResponseDto {
    
    private Long id;
    private UserResponseDto userInfo;
    private String level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Entity를 DTO로 변환하는 정적 메서드
    public static StudentResponseDto from(Student student) {
        return StudentResponseDto.builder()
                .id(student.getId())
                .userInfo(UserResponseDto.from(student.getUser()))
                .level(student.getLevel())
                .createdAt(student.getUser().getCreatedAt())
                .updatedAt(student.getUser().getUpdatedAt())
                .build();
    }
} 