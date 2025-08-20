package com.kt.backendapp.service;

import com.kt.backendapp.domain.vocab.Vocab;
import com.kt.backendapp.domain.vocab.VocabRepository;
import com.kt.backendapp.dto.vocab.VocabCreateDto;
import com.kt.backendapp.dto.vocab.VocabResponseDto;
import com.kt.backendapp.dto.vocab.VocabUpdateDto;
import com.kt.backendapp.exception.VocabNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VocabService {
    
    private final VocabRepository vocabRepository;
    
    // 단어 등록
    @Transactional
    public VocabResponseDto createVocab(VocabCreateDto createDto) {
        // 중복 단어 확인
        if (vocabRepository.findByWord(createDto.getWord()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 단어입니다: " + createDto.getWord());
        }
        
        Vocab vocab = Vocab.builder()
                .word(createDto.getWord())
                .definition(createDto.getDefinition())
                .build();
        
        Vocab savedVocab = vocabRepository.save(vocab);
        return VocabResponseDto.from(savedVocab);
    }
    
    // 전체 단어 목록 조회
    public List<VocabResponseDto> getAllVocabs() {
        List<Vocab> vocabs = vocabRepository.findAll();
        return vocabs.stream()
                .map(VocabResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 영단어로 검색
    public List<VocabResponseDto> searchVocabsByWord(String keyword) {
        List<Vocab> vocabs = vocabRepository.findByWordContainingIgnoreCase(keyword);
        return vocabs.stream()
                .map(VocabResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 한국어 뜻으로 검색
    public List<VocabResponseDto> searchVocabsByDefinition(String keyword) {
        List<Vocab> vocabs = vocabRepository.findByDefinitionContainingIgnoreCase(keyword);
        return vocabs.stream()
                .map(VocabResponseDto::from)
                .collect(Collectors.toList());
    }
    
    // 단어 상세 조회
    public VocabResponseDto getVocabById(Long vocabId) {
        Vocab vocab = vocabRepository.findById(vocabId)
                .orElseThrow(() -> new VocabNotFoundException("단어를 찾을 수 없습니다: " + vocabId));
        return VocabResponseDto.from(vocab);
    }
    
    // 단어 수정
    @Transactional
    public VocabResponseDto updateVocab(Long vocabId, VocabUpdateDto updateDto) {
        Vocab vocab = vocabRepository.findById(vocabId)
                .orElseThrow(() -> new VocabNotFoundException("단어를 찾을 수 없습니다: " + vocabId));
        
        if (updateDto.getWord() != null) {
            vocab.setWord(updateDto.getWord());
        }
        if (updateDto.getDefinition() != null) {
            vocab.setDefinition(updateDto.getDefinition());
        }
        
        return VocabResponseDto.from(vocab);
    }
    
    // 단어 삭제
    @Transactional
    public void deleteVocab(Long vocabId) {
        Vocab vocab = vocabRepository.findById(vocabId)
                .orElseThrow(() -> new VocabNotFoundException("단어를 찾을 수 없습니다: " + vocabId));
        
        vocabRepository.delete(vocab);
    }
} 