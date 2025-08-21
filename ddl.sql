-- users (부모 테이블)
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    user_type VARCHAR(20) NOT NULL, --student, teacher
    status VARCHAR(20) NOT NULL, -- active, inactive, suspended
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- teacher (users의 subtype)
CREATE TABLE teacher (
    id BIGINT PRIMARY KEY REFERENCES users(id)
);

-- student (users의 subtype)
CREATE TABLE student (
    id BIGINT PRIMARY KEY REFERENCES users(id),
    level VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- vocab
CREATE TABLE vocab (
    id BIGSERIAL PRIMARY KEY,
    word VARCHAR(100) NOT NULL,
    definition VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- quiz
CREATE TABLE quiz (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    numofquestion INT,
    open_at TIMESTAMP,
    close_at TIMESTAMP,
    time_limit_sec INT,
    target_score INT,
    created_by BIGINT REFERENCES teacher(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- question
CREATE TABLE question (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT NOT NULL REFERENCES quiz(id) ON DELETE CASCADE,
    vocab_id BIGINT NOT NULL REFERENCES vocab(id),
    stem TEXT,
    type VARCHAR(50), -- OX, multiple, dictation
    correct_answer VARCHAR(255) NOT NULL,
    points INT DEFAULT 1, -- 문제별 배점 (기본 1점)
    explanation TEXT,     -- 문제 해설
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- quiz_vocab (퀴즈와 단어 연결: 복합 PK)
CREATE TABLE quiz_vocab (
    quiz_id BIGINT NOT NULL REFERENCES quiz(id) ON DELETE CASCADE,
    vocab_id BIGINT NOT NULL REFERENCES vocab(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (quiz_id, vocab_id)
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
