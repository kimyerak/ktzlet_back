package com.kt.backendapp.controller;

import com.kt.backendapp.dto.user.UserRequestDto;
import com.kt.backendapp.dto.user.UserResponseDto;
import com.kt.backendapp.dto.user.UserUpdateDto;
import com.kt.backendapp.dto.user.UserRegistrationDto;
import com.kt.backendapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    // 사용자 가입 (Student 또는 Teacher)
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @RequestBody UserRegistrationDto registrationDto) {
        UserResponseDto responseDto = userService.registerUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam String email) {
        UserResponseDto responseDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto updateDto) {
        UserResponseDto responseDto = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(responseDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 학생만 조회
    @GetMapping("/students")
    public ResponseEntity<List<UserResponseDto>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }
} 