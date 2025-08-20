package com.kt.backendapp.service;

import com.kt.backendapp.domain.question.Question;
import com.kt.backendapp.domain.question.QuestionRepository;
import com.kt.backendapp.domain.question.QuestionType;
import com.kt.backendapp.domain.quiz.Quiz;
import com.kt.backendapp.domain.quiz.QuizRepository;
import com.kt.backendapp.domain.vocab.Vocab;
import com.kt.backendapp.domain.vocab.VocabRepository;
import com.kt.backendapp.dto.question.QuestionCreateDto;
import com.kt.backendapp.dto.question.QuestionResponseDto;
import com.kt.backendapp.dto.question.QuestionUpdateDto;
import com.kt.backendapp.exception.QuestionNotFoundException;
import com.kt.backendapp.exception.QuizNotFoundException;
import com.kt.backendapp.exception.VocabNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final VocabRepository vocabRepository;
    
    // 전체 문제 목록 조회
    public List<QuestionResponseDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(QuestionResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 문제 생성
    @Transactional
    public QuestionResponseDto createQuestion(QuestionCreateDto createDto) {
        // 퀴즈 존재 확인
        Quiz quiz = quizRepository.findById(createDto.getQuizId())
                .orElseThrow(() -> new QuizNotFoundException("퀴즈를 찾을 수 없습니다: " + createDto.getQuizId()));
        
        // 단어장 존재 확인 (받아쓰기 문제의 경우)
        Vocab vocab = null;
        if (createDto.getVocabId() != null) {
            vocab = vocabRepository.findById(createDto.getVocabId())
                    .orElseThrow(() -> new VocabNotFoundException("단어를 찾을 수 없습니다: " + createDto.getVocabId()));
        }
        
        Question question = Question.builder()
                .quiz(quiz)
                .type(createDto.getType())
                .stem(createDto.getStem())
                .correctAnswer(createDto.getCorrectAnswer())
                .explanation(createDto.getExplanation())
                .points(createDto.getPoints())
                .vocab(vocab)
                .build();
        
        Question savedQuestion = questionRepository.save(question);
        return QuestionResponseDto.from(savedQuestion);
    }
    
    // 퀴즈별 문제 목록 조회
    public List<QuestionResponseDto> getQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questions.stream()
                .map(QuestionResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 문제 상세 조회
    public QuestionResponseDto getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("문제를 찾을 수 없습니다: " + questionId));
        return QuestionResponseDto.from(question);
    }
    
    // 문제 수정
    @Transactional
    public QuestionResponseDto updateQuestion(Long questionId, QuestionUpdateDto updateDto) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("문제를 찾을 수 없습니다: " + questionId));
        
        if (updateDto.getType() != null) {
            question.setType(updateDto.getType());
        }
        if (updateDto.getStem() != null) {
            question.setStem(updateDto.getStem());
        }
        if (updateDto.getCorrectAnswer() != null) {
            question.setCorrectAnswer(updateDto.getCorrectAnswer());
        }
        if (updateDto.getExplanation() != null) {
            question.setExplanation(updateDto.getExplanation());
        }
        if (updateDto.getPoints() != null) {
            question.setPoints(updateDto.getPoints());
        }
        if (updateDto.getVocabId() != null) {
            Vocab vocab = vocabRepository.findById(updateDto.getVocabId())
                    .orElseThrow(() -> new VocabNotFoundException("단어를 찾을 수 없습니다: " + updateDto.getVocabId()));
            question.setVocab(vocab);
        }
        
        return QuestionResponseDto.from(question);
    }
    
    // 문제 삭제
    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("문제를 찾을 수 없습니다: " + questionId));
        
        questionRepository.delete(question);
    }
    
    // 문제 유형별 조회
    public List<QuestionResponseDto> getQuestionsByQuizIdAndType(Long quizId, QuestionType type) {
        List<Question> questions = questionRepository.findByQuizIdAndType(quizId, type);
        return questions.stream()
                .map(QuestionResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 단어장별 문제 목록 조회 (받아쓰기 문제)
    public List<QuestionResponseDto> getQuestionsByVocabId(Long vocabId) {
        List<Question> questions = questionRepository.findByVocabId(vocabId);
        return questions.stream()
                .map(QuestionResponseDto::from)
                .collect(Collectors.toList());
    }
} 