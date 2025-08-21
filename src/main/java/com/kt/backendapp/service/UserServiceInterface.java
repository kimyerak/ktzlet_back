package com.kt.backendapp.service;

import com.kt.backendapp.dto.user.UserRequestDto;
import com.kt.backendapp.dto.user.UserResponseDto;
import com.kt.backendapp.dto.user.UserUpdateDto;
import com.kt.backendapp.dto.user.UserRegistrationDto;

import java.util.List;

public interface UserServiceInterface {
    
    // 사용자 가입 (Student 또는 Teacher로 생성)
    UserResponseDto registerUser(UserRegistrationDto registrationDto);
    
    // 사용자 등록 (기존 메서드 - 호환성 유지)
    UserResponseDto createUser(UserRequestDto requestDto);
    
    // 모든 사용자 목록 조회
    List<UserResponseDto> getAllUsers();
    
    // ID로 사용자 조회
    UserResponseDto getUserById(Long id);
    
    // 이메일로 사용자 조회
    UserResponseDto getUserByEmail(String email);
    
    // 사용자 정보 수정
    UserResponseDto updateUser(Long id, UserUpdateDto updateDto);
    
    // 사용자 삭제
    void deleteUser(Long id);
} 