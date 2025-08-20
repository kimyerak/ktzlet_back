package com.kt.backendapp.domain.vocab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VocabRepository extends JpaRepository<Vocab, Long> {
    
    // 영단어로 검색 (부분 일치)
    List<Vocab> findByWordContainingIgnoreCase(String word);
    
    // 한국어 뜻으로 검색 (부분 일치)
    List<Vocab> findByDefinitionContainingIgnoreCase(String definition);
    
    // 영단어로 정확히 검색
    Optional<Vocab> findByWord(String word);
} 