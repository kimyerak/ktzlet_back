package com.kt.backendapp.dto.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserRegistrationDto {
    
    private UserRequestDto userInfo;
    private UserType userType; // STUDENT 또는 TEACHER
    
    // Student 특화 정보 (userType이 STUDENT인 경우)
    private String level;
    
    // Teacher 특화 정보 (userType이 TEACHER인 경우)
    private String subject;
    private String department;
    
    public enum UserType {
        STUDENT, TEACHER
    }
} 