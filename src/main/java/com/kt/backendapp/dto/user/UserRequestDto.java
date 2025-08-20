package com.kt.backendapp.dto.user;

import com.kt.backendapp.domain.user.UserRole;
import com.kt.backendapp.domain.user.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class UserRequestDto {
    

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
    

    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
    

    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 100, message = "이름은 2자 이상 100자 이하여야 합니다")
    private String name;
    

    @NotNull(message = "역할은 필수입니다")
    private UserRole role;
    

    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;
} 