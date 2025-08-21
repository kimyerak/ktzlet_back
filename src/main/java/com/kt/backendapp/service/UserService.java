package com.kt.backendapp.service;

import com.kt.backendapp.domain.user.User;
import com.kt.backendapp.domain.user.UserRepository;
import com.kt.backendapp.domain.user.UserStatus;
import com.kt.backendapp.domain.user.Student;
import com.kt.backendapp.domain.user.Teacher;
import com.kt.backendapp.domain.user.StudentRepository;
import com.kt.backendapp.domain.user.TeacherRepository;
import com.kt.backendapp.dto.user.UserRequestDto;
import com.kt.backendapp.dto.user.UserResponseDto;
import com.kt.backendapp.dto.user.UserUpdateDto;
import com.kt.backendapp.dto.user.UserRegistrationDto;
import com.kt.backendapp.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserServiceInterface {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    
    // 사용자 가입 (Student 또는 Teacher로 생성)
    @Transactional
    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        // null 체크 추가
        if (registrationDto == null || registrationDto.getUserInfo() == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다");
        }
        
        // 이메일 중복 확인 (이메일이 있는 경우에만)
        if (registrationDto.getUserInfo().getEmail() != null && 
            userRepository.existsByEmail(registrationDto.getUserInfo().getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + registrationDto.getUserInfo().getEmail());
        }
        
        // UserType 설정 (기본값: STUDENT)
        User.UserType userType = User.UserType.STUDENT;
        if (registrationDto.getUserType() != null) {
            userType = registrationDto.getUserType() == UserRegistrationDto.UserType.STUDENT ? 
                User.UserType.STUDENT : User.UserType.TEACHER;
        }
        
        // User 엔티티 생성 (null 값 처리)
        User user = User.builder()
                .email(registrationDto.getUserInfo().getEmail() != null ? registrationDto.getUserInfo().getEmail() : "")
                .password(registrationDto.getUserInfo().getPassword() != null ? registrationDto.getUserInfo().getPassword() : "")
                .name(registrationDto.getUserInfo().getName() != null ? registrationDto.getUserInfo().getName() : "")
                .userType(userType)
                .status(registrationDto.getUserInfo().getStatus() != null ? registrationDto.getUserInfo().getStatus() : UserStatus.ACTIVE)
                .build();
        
        User savedUser = userRepository.save(user);
        
        // 서브타입 생성 (Student 또는 Teacher)
        if (registrationDto.getUserType() == UserRegistrationDto.UserType.STUDENT) {
            Student student = Student.builder()
                    .user(savedUser)
                    .level(registrationDto.getLevel())
                    .build();
            studentRepository.save(student);
        } else if (registrationDto.getUserType() == UserRegistrationDto.UserType.TEACHER) {
            Teacher teacher = Teacher.builder()
                    .user(savedUser)
                    .build();
            teacherRepository.save(teacher);
        }
        
        return UserResponseDto.from(savedUser);
    }
    
    // 사용자 등록 (기존 메서드 - 호환성 유지)
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + requestDto.getEmail());
        }
        
        User user = User.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
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

    // 학생 전용 조회
public List<UserResponseDto> getAllStudents() {
    return studentRepository.findAll().stream()
            .map(Student::getUser) // Student -> User 꺼내기
            .map(UserResponseDto::from)
            .collect(Collectors.toList());
}

} 