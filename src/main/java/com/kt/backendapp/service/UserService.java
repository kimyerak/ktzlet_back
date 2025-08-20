package com.kt.backendapp.service;

import com.kt.backendapp.domain.user.User;
import com.kt.backendapp.domain.user.UserRepository;
import com.kt.backendapp.domain.user.UserRole;
import com.kt.backendapp.domain.user.UserStatus;
import com.kt.backendapp.dto.user.UserRequestDto;
import com.kt.backendapp.dto.user.UserResponseDto;
import com.kt.backendapp.dto.user.UserUpdateDto;
import com.kt.backendapp.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // 사용자 등록
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + requestDto.getEmail());
        }
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(encodedPassword)
                .name(requestDto.getName())
                .role(requestDto.getRole())
                .status(requestDto.getStatus())
                .build();
        
        User savedUser = userRepository.save(user);
        return UserResponseDto.from(savedUser);
    }
    
    // 사용자 조회 (ID)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        return UserResponseDto.from(user);
    }
    
    // 사용자 조회 (이메일)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + email));
        return UserResponseDto.from(user);
    }
    
    // 전체 사용자 목록 조회
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 역할별 사용자 목록 조회
    public List<UserResponseDto> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 활성 학생 목록 조회
    public List<UserResponseDto> getActiveStudents() {
        return userRepository.findActiveStudents().stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 활성 교사 목록 조회
    public List<UserResponseDto> getActiveTeachers() {
        return userRepository.findActiveTeachers().stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 사용자 정보 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto updateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        
        if (updateDto.getName() != null) {
            user.setName(updateDto.getName());
        }
        
        if (updateDto.getStatus() != null) {
            user.setStatus(updateDto.getStatus());
        }
        
        return UserResponseDto.from(user);
    }
    
    // 사용자 상태 변경
    @Transactional
    public UserResponseDto updateUserStatus(Long id, UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        
        user.setStatus(status);
        return UserResponseDto.from(user);
    }
    
    // 사용자 삭제 (소프트 삭제 - 상태를 SUSPENDED로 변경)
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다: " + id));
        
        user.setStatus(UserStatus.SUSPENDED);
    }
} 