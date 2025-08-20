-- 사용자 테이블
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL, -- student, teacher
    status VARCHAR(20) NOT NULL, -- active, inactive, suspended
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 단어장 테이블
CREATE TABLE vocab (
    id BIGSERIAL PRIMARY KEY,
    word VARCHAR(100) NOT NULL, -- 영단어
    definition VARCHAR(100), -- 한국어 뜻
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 퀴즈 테이블
CREATE TABLE quiz (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    numofquestion INTEGER,
    open_at TIMESTAMP,
    close_at TIMESTAMP,
    time_limit_sec INTEGER,
    target_score INTEGER,
    created_by BIGINT REFERENCES users(id), -- teacher FK
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 문제 테이블 (단일 PK + 복합 unique)
CREATE TABLE question (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT REFERENCES quiz(id),
    vocab_id BIGINT REFERENCES vocab(id),
    type VARCHAR(10) NOT NULL, -- OX, short, etc
    stem TEXT NOT NULL,
    correct_answer TEXT NOT NULL,
    explanation TEXT,
    points INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (quiz_id, vocab_id)
);

-- 학생별 퀴즈 응시 테이블 (복합 PK)
CREATE TABLE quiz_per_student (
    quiz_id BIGINT REFERENCES quiz(id),
    student_id BIGINT REFERENCES users(id),
    started_at TIMESTAMP,
    submitted_at TIMESTAMP,
    total_score INTEGER,
    pass BOOLEAN,
    status VARCHAR(30), -- assigned, in_progress, submitted, late
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (quiz_id, student_id)
);

-- 학생별 문제 답안 테이블
CREATE TABLE response_per_question (
    id BIGSERIAL PRIMARY KEY,
    student_id BIGINT REFERENCES users(id), -- role=student
    question_id BIGINT REFERENCES question(id),
    response VARCHAR(30),
    is_correct BOOLEAN,
    retry_answer VARCHAR(30),
    retry_result VARCHAR(30),
    retry_count INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 제약조건 추가
ALTER TABLE quiz ADD CONSTRAINT check_close_at_target_score 
    CHECK (close_at IS NULL OR target_score IS NULL OR close_at > CURRENT_TIMESTAMP);

-- 인덱스 생성
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_question_quiz_id ON question(quiz_id);
CREATE INDEX idx_response_student_id ON response_per_question(student_id);
CREATE INDEX idx_response_question_id ON response_per_question(question_id);