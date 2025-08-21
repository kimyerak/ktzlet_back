package com.kt.backendapp.service;

import com.kt.backendapp.domain.quiz.Quiz;
import com.kt.backendapp.domain.quiz.QuizRepository;
import com.kt.backendapp.domain.user.Teacher;
import com.kt.backendapp.domain.user.TeacherRepository;
import com.kt.backendapp.domain.vocab.Vocab;
import com.kt.backendapp.domain.vocab.VocabRepository;
import com.kt.backendapp.dto.quiz.QuizCreateDto;
import com.kt.backendapp.dto.quiz.QuizResponseDto;
import com.kt.backendapp.dto.quiz.QuizUpdateDto;
import com.kt.backendapp.exception.QuizNotFoundException;
import com.kt.backendapp.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {
    
    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    private final VocabRepository vocabRepository;
    
    // 퀴즈 생성
    @Transactional
    public QuizResponseDto createQuiz(QuizCreateDto createDto) {
        // 교사 존재 확인
        Teacher teacher = teacherRepository.findById(createDto.getCreatedBy())
                .orElseThrow(() -> new UserNotFoundException("교사를 찾을 수 없습니다: " + createDto.getCreatedBy()));
        
        Quiz quiz = Quiz.builder()
                .title(createDto.getTitle())
                .numOfQuestions(createDto.getNumOfQuestions())
                .openAt(createDto.getOpenAt())
                .closeAt(createDto.getCloseAt())
                .timeLimitSec(createDto.getTimeLimitSec())
                .targetScore(createDto.getTargetScore())
                .createdBy(teacher)
                .build();
        
        // ✅ vocabIds 처리
    if (createDto.getVocabIds() != null && !createDto.getVocabIds().isEmpty()) {
        Set<Vocab> vocabs = vocabRepository.findAllById(createDto.getVocabIds())
                .stream().collect(Collectors.toSet());
        quiz.setVocabs(vocabs);
    }

    Quiz saved = quizRepository.save(quiz);
    return QuizResponseDto.from(saved);
    }
    
    // 교사별 퀴즈 목록 조회
    public List<QuizResponseDto> getQuizzesByTeacher(Long teacherId) {
        List<Quiz> quizzes = quizRepository.findByCreatedById(teacherId);
        return quizzes.stream()
                .map(QuizResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 퀴즈 상세 조회
    public QuizResponseDto getQuizById(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        return QuizResponseDto.from(quiz);
    }
    
    // 퀴즈 수정
    @Transactional
    public QuizResponseDto updateQuiz(Long quizId, QuizUpdateDto updateDto) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        
        if (updateDto.getTitle() != null) {
            quiz.setTitle(updateDto.getTitle());
        }
        if (updateDto.getNumOfQuestions() != null) {
            quiz.setNumOfQuestions(updateDto.getNumOfQuestions());
        }
        if (updateDto.getOpenAt() != null) {
            quiz.setOpenAt(updateDto.getOpenAt());
        }
        if (updateDto.getCloseAt() != null) {
            quiz.setCloseAt(updateDto.getCloseAt());
        }
        if (updateDto.getTimeLimitSec() != null) {
            quiz.setTimeLimitSec(updateDto.getTimeLimitSec());
        }
        if (updateDto.getTargetScore() != null) {
            quiz.setTargetScore(updateDto.getTargetScore());
        }
        
        return QuizResponseDto.from(quiz);
    }
    
    // 퀴즈 삭제
    @Transactional
    public void deleteQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        
        quizRepository.delete(quiz);
    }
    
    // 퀴즈 상태 변경
    @Transactional
    public QuizResponseDto updateQuizStatus(Long quizId, String status) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        
        // 상태 변경 로직 (필요에 따라 구현)
        // 예: 공개/비공개, 활성/비활성 등
        
        return QuizResponseDto.from(quiz);
    }
    
    // 활성 퀴즈 목록 조회
    public List<QuizResponseDto> getActiveQuizzes() {
        List<Quiz> quizzes = quizRepository.findActiveQuizzes(LocalDateTime.now());
        return quizzes.stream()
                .map(QuizResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 학생이 응시 가능한 퀴즈 목록 조회
    public List<QuizResponseDto> getAvailableQuizzesForStudent(Long studentId) {
        List<Quiz> quizzes = quizRepository.findAvailableQuizzesForStudent(studentId, LocalDateTime.now());
        return quizzes.stream()
                .map(QuizResponseDto::from)
                .collect(Collectors.toList());
    }
// 퀴즈 전체 조회 
public List<QuizResponseDto> getAllQuizzes() { List<Quiz> quizzes = quizRepository.findAll(); return quizzes.stream() .map(QuizResponseDto::from) .collect(Collectors.toList()); }
    
} 