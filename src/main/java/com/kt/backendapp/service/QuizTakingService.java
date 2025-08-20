package com.kt.backendapp.service;

import com.kt.backendapp.domain.question.Question;
import com.kt.backendapp.domain.question.QuestionRepository;
import com.kt.backendapp.domain.quiz.Quiz;
import com.kt.backendapp.domain.quiz.QuizPerStudent;
import com.kt.backendapp.domain.quiz.QuizPerStudentRepository;
import com.kt.backendapp.domain.quiz.QuizPerStudentStatus;
import com.kt.backendapp.domain.quiz.QuizRepository;
import com.kt.backendapp.domain.quiz.ResponsePerQuestion;
import com.kt.backendapp.domain.quiz.ResponsePerQuestionRepository;
import com.kt.backendapp.domain.user.User;
import com.kt.backendapp.domain.user.UserRepository;
import com.kt.backendapp.dto.quiztaking.QuizTakingDto;
import com.kt.backendapp.dto.quiztaking.QuizTakingResponseDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerResponseDto;
import com.kt.backendapp.exception.QuizNotFoundException;
import com.kt.backendapp.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizTakingService {
    
    private final QuizPerStudentRepository quizPerStudentRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final ResponsePerQuestionRepository responsePerQuestionRepository;
    
    // 학생이 응시 가능한 퀴즈 목록 조회
    public List<QuizTakingResponseDto> getAvailableQuizzes(Long studentId) {
        // 학생 존재 확인
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + studentId));
        
        // 현재 시간 기준으로 활성 퀴즈 조회
        List<Quiz> availableQuizzes = quizRepository.findAvailableQuizzesForStudent(studentId, LocalDateTime.now());
        
        return availableQuizzes.stream()
                .map(quiz -> QuizTakingResponseDto.builder()
                        .quizId(quiz.getId())
                        .quizTitle(quiz.getTitle())
                        .studentId(studentId)
                        .studentName(student.getName())
                        .build())
                .collect(Collectors.toList());
    }
    
    // 퀴즈 시작
    @Transactional
    public QuizTakingResponseDto startQuiz(Long quizId, Long studentId) {
        // 퀴즈 존재 확인
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        
        // 학생 존재 확인
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + studentId));
        
        // 이미 응시한 퀴즈인지 확인
        quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId)
                .ifPresent(qps -> {
                    throw new IllegalStateException("이미 응시한 퀴즈입니다.");
                });
        
        // 퀴즈 응시 기록 생성 (복합 PK 사용)
        QuizPerStudent.QuizPerStudentId id = new QuizPerStudent.QuizPerStudentId();
        id.setQuizId(quizId);
        id.setStudentId(studentId);
        
        QuizPerStudent quizPerStudent = QuizPerStudent.builder()
                .id(id)
                .quiz(quiz)
                .student(student)
                .startedAt(LocalDateTime.now())
                .status(QuizPerStudentStatus.IN_PROGRESS)
                .build();
        
        QuizPerStudent savedQuizPerStudent = quizPerStudentRepository.save(quizPerStudent);
        return QuizTakingResponseDto.from(savedQuizPerStudent);
    }
    
    // 문제별 답안 제출 (실시간 채점)
    @Transactional
    public QuestionAnswerResponseDto submitQuestionAnswer(Long questionId, QuestionAnswerDto answerDto) {
        // 문제 존재 확인
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("문제를 찾을 수 없습니다: " + questionId));
        
        // 학생 존재 확인
        User student = userRepository.findById(answerDto.getStudentId())
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + answerDto.getStudentId()));
        
        // 기존 답안 조회
        Optional<ResponsePerQuestion> existingResponse = responsePerQuestionRepository
                .findByStudentIdAndQuestionId(answerDto.getStudentId(), questionId);
        
        ResponsePerQuestion responsePerQuestion;
        
        if (existingResponse.isPresent()) {
            // 기존 답안이 있으면 재시도로 처리
            responsePerQuestion = existingResponse.get();
            responsePerQuestion.setRetryAnswer(answerDto.getAnswer());
            responsePerQuestion.setRetryCount(responsePerQuestion.getRetryCount() + 1);
            
            // 재시도 답안 채점
            boolean retryCorrect = isAnswerCorrect(question, answerDto.getAnswer());
            responsePerQuestion.setRetryResult(retryCorrect ? "CORRECT" : "INCORRECT");
            
        } else {
            // 새로운 답안 제출
            boolean isCorrect = isAnswerCorrect(question, answerDto.getAnswer());
            
            responsePerQuestion = ResponsePerQuestion.builder()
                    .student(student)
                    .question(question)
                    .response(answerDto.getAnswer())
                    .isCorrect(isCorrect)
                    .retryCount(0)
                    .build();
        }
        
        ResponsePerQuestion savedResponse = responsePerQuestionRepository.save(responsePerQuestion);
        
        // 다음 문제 ID 찾기
        Long nextQuestionId = findNextQuestionId(question.getQuiz().getId(), questionId);
        
        return QuestionAnswerResponseDto.builder()
                .questionId(questionId)
                .studentId(answerDto.getStudentId())
                .questionStem(question.getStem())
                .submittedAnswer(answerDto.getAnswer())
                .correctAnswer(question.getCorrectAnswer())
                .isCorrect(responsePerQuestion.getIsCorrect())
                .points(question.getPoints())
                .retryAnswer(responsePerQuestion.getRetryAnswer())
                .retryResult(responsePerQuestion.getRetryResult())
                .retryCount(responsePerQuestion.getRetryCount())
                .submittedAt(savedResponse.getCreatedAt())
                .canRetry(responsePerQuestion.getRetryCount() < 3) // 최대 3번까지 재시도 가능
                .nextQuestionId(nextQuestionId)
                .build();
    }
    
    // 퀴즈 완료 (최종 제출)
    @Transactional
    public QuizTakingResponseDto completeQuiz(Long quizId, Long studentId) {
        // 퀴즈 응시 기록 조회
        QuizPerStudent quizPerStudent = quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId)
                .orElseThrow(() -> new IllegalStateException("퀴즈 응시 기록을 찾을 수 없습니다."));
        
        // 학생의 답안들 조회
        List<ResponsePerQuestion> responses = responsePerQuestionRepository
                .findByStudentIdAndQuizId(studentId, quizId);
        
        // 총점 계산
        int totalScore = responses.stream()
                .mapToInt(response -> {
                    if (response.getIsCorrect()) {
                        return response.getQuestion().getPoints() != null ? response.getQuestion().getPoints() : 0;
                    }
                    return 0;
                })
                .sum();
        
        // 합격 여부 판정 (targetScore가 null이면 항상 합격)
        boolean pass = quizPerStudent.getQuiz().getTargetScore() == null || 
                      totalScore >= quizPerStudent.getQuiz().getTargetScore();
        
        // 퀴즈 응시 기록 업데이트
        quizPerStudent.setSubmittedAt(LocalDateTime.now());
        quizPerStudent.setTotalScore(totalScore);
        quizPerStudent.setPass(pass);
        quizPerStudent.setStatus(QuizPerStudentStatus.SUBMITTED);
        
        QuizPerStudent savedQuizPerStudent = quizPerStudentRepository.save(quizPerStudent);
        
        return QuizTakingResponseDto.from(savedQuizPerStudent);
    }
    
    // 다음 문제 ID 찾기
    private Long findNextQuestionId(Long quizId, Long currentQuestionId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).getId().equals(currentQuestionId) && i + 1 < questions.size()) {
                return questions.get(i + 1).getId();
            }
        }
        return null; // 마지막 문제인 경우
    }
    
    // 답안 정답 여부 판정
    private boolean isAnswerCorrect(Question question, String studentAnswer) {
        if (studentAnswer == null || studentAnswer.trim().isEmpty()) {
            return false;
        }
        
        String correctAnswer = question.getCorrectAnswer();
        return studentAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
    
    // 학생 성적 히스토리 조회
    public List<QuizTakingResponseDto> getStudentHistory(Long studentId) {
        List<QuizPerStudent> history = quizPerStudentRepository.findRecentQuizzesByStudentId(studentId);
        return history.stream()
                .map(QuizTakingResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 특정 퀴즈 응시 결과 조회
    public QuizTakingResponseDto getQuizResult(Long quizId, Long studentId) {
        QuizPerStudent quizPerStudent = quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId)
                .orElseThrow(() -> new IllegalStateException("퀴즈 응시 기록을 찾을 수 없습니다."));
        
        return QuizTakingResponseDto.from(quizPerStudent);
    }
} 