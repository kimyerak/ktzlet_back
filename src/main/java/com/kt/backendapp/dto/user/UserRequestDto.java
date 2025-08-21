package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserRequestDto {
    
    private String email;
    private String password;
    private String name;
    
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
} 