package com.kt.backendapp.service;

import com.kt.backendapp.dto.vocab.VocabCreateDto;
import com.kt.backendapp.dto.vocab.VocabResponseDto;
import com.kt.backendapp.dto.vocab.VocabUpdateDto;

import java.util.List;

public interface VocabServiceInterface {
    
    // 단어 등록
    VocabResponseDto createVocab(VocabCreateDto createDto);
    
    // 모든 단어 목록 조회
    List<VocabResponseDto> getAllVocabs();
    
    // ID로 단어 조회
    VocabResponseDto getVocabById(Long id);
    
    // 영단어로 검색
    List<VocabResponseDto> searchVocabsByWord(String keyword);
    
    // 한국어 뜻으로 검색
    List<VocabResponseDto> searchVocabsByDefinition(String keyword);
    
    // 단어 수정
    VocabResponseDto updateVocab(Long id, VocabUpdateDto updateDto);
    
    // 단어 삭제
    void deleteVocab(Long id);
} 