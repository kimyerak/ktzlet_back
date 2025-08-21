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
import com.kt.backendapp.domain.user.Student;
import com.kt.backendapp.domain.user.StudentRepository;
import com.kt.backendapp.dto.quiztaking.QuizTakingDto;
import com.kt.backendapp.dto.quiztaking.QuizTakingResponseDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerDto;
import com.kt.backendapp.dto.quiztaking.QuestionAnswerResponseDto;
import com.kt.backendapp.dto.quiz.QuizSubmissionDto;
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
public class QuizTakingService implements QuizTakingServiceInterface {
    
    private final QuizPerStudentRepository quizPerStudentRepository;
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;
    private final QuestionRepository questionRepository;
    private final ResponsePerQuestionRepository responsePerQuestionRepository;
    
    // 학생이 응시 가능한 퀴즈 목록 조회
    public List<QuizTakingResponseDto> getAvailableQuizzes(Long studentId) {
        // 학생 존재 확인
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + studentId));
        
        // 현재 시간 기준으로 활성 퀴즈 조회
        List<Quiz> availableQuizzes = quizRepository.findAvailableQuizzesForStudent(studentId, LocalDateTime.now());
        
        return availableQuizzes.stream()
                .map(quiz -> QuizTakingResponseDto.fromQuiz(quiz, studentId, student.getUser().getName()))
                .collect(Collectors.toList());
    }
    
    // 퀴즈 응시 시작
    @Transactional
    public QuizTakingResponseDto startQuiz(Long quizId, Long studentId) {
        // 퀴즈 존재 확인
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + quizId));
        
        // 학생 존재 확인
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + studentId));
        
        // 기존 응시 기록 조회
        Optional<QuizPerStudent> existingQuizPerStudent = quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId);
        
        QuizPerStudent quizPerStudent;
        
        if (existingQuizPerStudent.isPresent()) {
            // 기존 기록이 있으면 상태만 업데이트
            quizPerStudent = existingQuizPerStudent.get();
            quizPerStudent.setStartedAt(LocalDateTime.now());
            quizPerStudent.setStatus(QuizPerStudentStatus.IN_PROGRESS);
            quizPerStudent.setSubmittedAt(null);
            quizPerStudent.setTotalScore(null);
            quizPerStudent.setPass(null);
        } else {
            // 새로운 응시 기록 생성
            QuizPerStudent.QuizPerStudentId id = new QuizPerStudent.QuizPerStudentId();
            id.setQuizId(quizId);
            id.setStudentId(studentId);
            
            quizPerStudent = QuizPerStudent.builder()
                    .id(id)
                    .quiz(quiz)
                    .student(student)
                    .startedAt(LocalDateTime.now())
                    .status(QuizPerStudentStatus.IN_PROGRESS)
                    .build();
        }
        
        QuizPerStudent savedQuizPerStudent = quizPerStudentRepository.save(quizPerStudent);
        return QuizTakingResponseDto.from(savedQuizPerStudent);
    }
    
    // 문제별 답안 제출
    @Transactional
    public QuestionAnswerResponseDto submitAnswer(QuestionAnswerDto answerDto) {
        // 문제 존재 확인
        Question question = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new QuizNotFoundException("문제를 찾을 수 없습니다: " + answerDto.getQuestionId()));
        
        // 학생 존재 확인
        Student student = studentRepository.findById(answerDto.getStudentId())
                .orElseThrow(() -> new UserNotFoundException("학생을 찾을 수 없습니다: " + answerDto.getStudentId()));
        
        // 정답 여부 확인
        boolean isCorrect = question.getCorrectAnswer().equals(answerDto.getAnswer());
        
        // 기존 답안 조회
        Optional<ResponsePerQuestion> existingResponse = responsePerQuestionRepository
                .findByStudentIdAndQuestionId(answerDto.getStudentId(), answerDto.getQuestionId());
        
        ResponsePerQuestion response;
        
        if (existingResponse.isPresent()) {
            // 기존 답안이 있으면 업데이트
            response = existingResponse.get();
            response.setResponse(answerDto.getAnswer());
            response.setIsCorrect(isCorrect);
        } else {
            // 새로운 답안 생성
            response = ResponsePerQuestion.builder()
                    .student(student)
                    .question(question)
                    .response(answerDto.getAnswer())
                    .isCorrect(isCorrect)
                    .build();
        }
        
        ResponsePerQuestion savedResponse = responsePerQuestionRepository.save(response);
        
        // 응답 DTO 생성
        return QuestionAnswerResponseDto.builder()
                .questionId(answerDto.getQuestionId())
                .studentId(answerDto.getStudentId())
                .questionStem(question.getStem())
                .submittedAnswer(answerDto.getAnswer())
                .correctAnswer(question.getCorrectAnswer())
                .isCorrect(isCorrect)
                .points(isCorrect ? question.getPoints() : 0)
                .explanation(question.getExplanation())
                .submittedAt(LocalDateTime.now())
                .build();
    }
    
    // 퀴즈 제출 완료
    @Transactional
    public QuizTakingResponseDto submitQuiz(Long quizId, Long studentId) {
        // 퀴즈 응시 기록 조회
        QuizPerStudent quizPerStudent = quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈 응시 기록을 찾을 수 없습니다"));
        
        // 총점 계산
        List<ResponsePerQuestion> responses = responsePerQuestionRepository.findByStudentId(studentId);
        int totalScore = responses.stream()
                .filter(ResponsePerQuestion::getIsCorrect)
                .mapToInt(response -> response.getQuestion().getPoints())
                .sum();
        
        // 합격 여부 확인
        boolean pass = totalScore >= quizPerStudent.getQuiz().getTargetScore();
        
        // 퀴즈 응시 기록 업데이트
        quizPerStudent.setSubmittedAt(LocalDateTime.now());
        quizPerStudent.setTotalScore(totalScore);
        quizPerStudent.setPass(pass);
        quizPerStudent.setStatus(QuizPerStudentStatus.SUBMITTED);
        
        QuizPerStudent savedQuizPerStudent = quizPerStudentRepository.save(quizPerStudent);
        return QuizTakingResponseDto.from(savedQuizPerStudent);
    }
    
    // 학생의 퀴즈 응시 결과 조회
    public List<QuizTakingResponseDto> getQuizResults(Long studentId) {
        List<QuizPerStudent> quizResults = quizPerStudentRepository.findById_StudentId(studentId);
        return quizResults.stream()
                .map(QuizTakingResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 특정 퀴즈 응시 결과 조회
    public QuizTakingResponseDto getQuizResult(Long quizId, Long studentId) {
        QuizPerStudent quizPerStudent = quizPerStudentRepository.findById_QuizIdAndId_StudentId(quizId, studentId)
                .orElseThrow(() -> new QuizNotFoundException("퀴즈 응시 기록을 찾을 수 없습니다"));
        return QuizTakingResponseDto.from(quizPerStudent);
    }
} 