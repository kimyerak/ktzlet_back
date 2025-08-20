package com.kt.backendapp.controller;

import com.kt.backendapp.domain.user.UserRole;
import com.kt.backendapp.dto.user.UserRequestDto;
import com.kt.backendapp.dto.user.UserResponseDto;
import com.kt.backendapp.dto.user.UserUpdateDto;
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
    
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = userService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto responseDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResponseDto>> getUsersByRole(@PathVariable UserRole role) {
        List<UserResponseDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/students/active")
    public ResponseEntity<List<UserResponseDto>> getActiveStudents() {
        List<UserResponseDto> students = userService.getActiveStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/teachers/active")
    public ResponseEntity<List<UserResponseDto>> getActiveTeachers() {
        List<UserResponseDto> teachers = userService.getActiveTeachers();
        return ResponseEntity.ok(teachers);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto updateDto) {
        UserResponseDto responseDto = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(responseDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
} 