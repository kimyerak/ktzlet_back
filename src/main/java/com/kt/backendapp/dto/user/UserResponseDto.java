package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.User;
import com.kt.backendapp.domain.user.UserStatus;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserResponseDto {
    
    private Long id;
    private String email;
    private String name;
    private String userType;
    private UserStatus status;
    private LocalDateTime createdAt;
    
    // Entity를 DTO로 변환하는 정적 메서드
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .userType(user.getUserType().name())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }
} 