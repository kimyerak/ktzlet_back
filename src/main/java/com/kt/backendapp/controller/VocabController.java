package com.kt.backendapp.controller;

import com.kt.backendapp.dto.vocab.VocabCreateDto;
import com.kt.backendapp.dto.vocab.VocabResponseDto;
import com.kt.backendapp.dto.vocab.VocabUpdateDto;
import com.kt.backendapp.service.VocabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabs")
@RequiredArgsConstructor
public class VocabController {
    
    private final VocabService vocabService;
    
    // 단어 등록
    @PostMapping
    public ResponseEntity<VocabResponseDto> createVocab(
            @Valid @RequestBody VocabCreateDto createDto) {
        VocabResponseDto responseDto = vocabService.createVocab(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    // 단어 목록 조회
    @GetMapping
    public ResponseEntity<List<VocabResponseDto>> getAllVocabs() {
        List<VocabResponseDto> vocabs = vocabService.getAllVocabs();
        return ResponseEntity.ok(vocabs);
    }
    
    // 영단어로 검색
    @GetMapping("/search/word")
    public ResponseEntity<List<VocabResponseDto>> searchVocabsByWord(@RequestParam String keyword) {
        List<VocabResponseDto> vocabs = vocabService.searchVocabsByWord(keyword);
        return ResponseEntity.ok(vocabs);
    }
    
    // 한국어 뜻으로 검색
    @GetMapping("/search/definition")
    public ResponseEntity<List<VocabResponseDto>> searchVocabsByDefinition(@RequestParam String keyword) {
        List<VocabResponseDto> vocabs = vocabService.searchVocabsByDefinition(keyword);
        return ResponseEntity.ok(vocabs);
    }
    
    // 단어 상세 조회
    @GetMapping("/{vocabId}")
    public ResponseEntity<VocabResponseDto> getVocabById(@PathVariable Long vocabId) {
        VocabResponseDto vocab = vocabService.getVocabById(vocabId);
        return ResponseEntity.ok(vocab);
    }
    
    // 단어 수정
    @PutMapping("/{vocabId}")
    public ResponseEntity<VocabResponseDto> updateVocab(
            @PathVariable Long vocabId,
            @Valid @RequestBody VocabUpdateDto updateDto) {
        VocabResponseDto responseDto = vocabService.updateVocab(vocabId, updateDto);
        return ResponseEntity.ok(responseDto);
    }
    
    // 단어 삭제
    @DeleteMapping("/{vocabId}")
    public ResponseEntity<Void> deleteVocab(@PathVariable Long vocabId) {
        vocabService.deleteVocab(vocabId);
        return ResponseEntity.noContent().build();
    }
} 