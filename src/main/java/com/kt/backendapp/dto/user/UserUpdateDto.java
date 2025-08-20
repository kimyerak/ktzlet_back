package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.UserStatus;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class UserUpdateDto {
    
    @Size(min = 2, max = 100, message = "이름은 2자 이상 100자 이하여야 합니다")
    private String name;
    
    private UserStatus status;
} 