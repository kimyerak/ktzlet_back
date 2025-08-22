-- 초기 유저 추가
INSERT INTO users (email, password, name, status) 
VALUES ('admin@test.com', '1234', '관리자', 'active');

-- 기본 단어장 데이터
INSERT INTO vocab (word, definition) 
VALUES ('apple', '사과'),
       ('banana', '바나나'),
       ('cat', '고양이');

-- 테스트용 퀴즈 추가
INSERT INTO quiz (title, num_of_question, created_by) 
VALUES ('기본 영어 단어 퀴즈', 2, 1);

-- 문제 추가
INSERT INTO question (quiz_id, vocab_id, stem, type, correct_answer, points, explanation)
VALUES (1, 1, '사과를 영어로 쓰세요', '주관식', 'apple', 1, 'apple은 사과입니다.'),
       (1, 2, '바나나를 영어로 쓰세요', '주관식', 'banana', 1, 'banana는 바나나입니다.');
