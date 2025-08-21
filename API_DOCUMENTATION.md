# 영어학습 플랫폼 API 문서

## 기본 정보
- **Base URL**: `http://localhost:8080`
- **Content-Type**: `application/json`

---

## 🔐 사용자 관리 API

### 1. 사용자 회원가입
**POST** `/api/users/register`

```json
{
  "userInfo": {
    "email": "student1@test.com",
    "password": "password123",
    "name": "김학생"
  },
  "userType": "STUDENT",
  "level": "초급"
}
```

**응답:**
```json
{
  "id": 1,
  "email": "student1@test.com",
  "name": "김학생",
  "userType": "STUDENT",
  "status": "ACTIVE",
  "createdAt": "2025-08-20T17:55:02.566479",
  "updatedAt": "2025-08-20T17:55:02.566479"
}
```

### 2. 교사 회원가입
**POST** `/api/users/register`

```json
{
  "userInfo": {
    "email": "teacher1@test.com",
    "password": "password123",
    "name": "이선생"
  },
  "userType": "TEACHER"
}
```

### 3. 모든 사용자 조회
**GET** `/api/users`

### 4. 특정 사용자 조회
**GET** `/api/users/{id}`

### 5. 이메일로 사용자 조회
**GET** `/api/users/email?email=student1@test.com`

### 6. 사용자 정보 수정
**PUT** `/api/users/{id}`

```json
{
  "name": "수정된 이름",
  "status": "ACTIVE"
}
```

### 7. 사용자 삭제
**DELETE** `/api/users/{id}`

---

## 📚 단어장 관리 API

### 1. 단어 등록
**POST** `/api/vocabs`

```json
{
  "word": "apple",
  "definition": "사과"
}
```

**응답:**
```json
{
  "id": 1,
  "word": "apple",
  "definition": "사과",
  "createdAt": "2025-08-20T17:55:02.566479",
  "updatedAt": "2025-08-20T17:55:02.566479"
}
```

### 2. 모든 단어 조회
**GET** `/api/vocabs`

### 3. 특정 단어 조회
**GET** `/api/vocabs/{id}`

### 4. 단어 수정
**PUT** `/api/vocabs/{id}`

```json
{
  "word": "banana",
  "definition": "바나나"
}
```

### 5. 단어 삭제
**DELETE** `/api/vocabs/{id}`

---

## 📝 퀴즈 관리 API

### 1. 퀴즈 생성 (교사만 가능)
**POST** `/api/quizzes`

```json
{
  "title": "기초 영단어 퀴즈",
  "numOfQuestions": 10,
  "openAt": "2025-08-21T09:00:00",
  "closeAt": "2025-08-21T18:00:00",
  "timeLimitSec": 1800,
  "targetScore": 80,
  "createdBy": 1
}
```

**응답:**
```json
{
  "id": 1,
  "title": "기초 영단어 퀴즈",
  "numOfQuestions": 10,
  "openAt": "2025-08-21T09:00:00",
  "closeAt": "2025-08-21T18:00:00",
  "timeLimitSec": 1800,
  "targetScore": 80,
  "createdBy": 1,
  "creatorName": "이선생",
  "createdAt": "2025-08-20T17:55:02.566479",
  "updatedAt": "2025-08-20T17:55:02.566479"
}
```

### 2. 모든 퀴즈 조회
**GET** `/api/quizzes`

### 3. 특정 퀴즈 조회
**GET** `/api/quizzes/{id}`

### 4. 교사별 퀴즈 조회
**GET** `/api/quizzes/teacher/{teacherId}`

### 5. 활성 퀴즈 조회
**GET** `/api/quizzes/active`

### 6. 학생이 응시 가능한 퀴즈 조회
**GET** `/api/quizzes/available/{studentId}`

### 7. 퀴즈 수정
**PUT** `/api/quizzes/{id}`

```json
{
  "title": "수정된 퀴즈 제목",
  "timeLimitSec": 2400
}
```

### 8. 퀴즈 삭제
**DELETE** `/api/quizzes/{id}`

---

## ❓ 문제 관리 API

### 1. 문제 생성
**POST** `/api/questions`

```json
{
  "quizId": 1,
  "vocabId": 1,
  "type": "OX",
  "stem": "Apple의 뜻은 사과이다.",
  "correctAnswer": "O",
  "explanation": "Apple은 사과를 의미합니다.",
  "points": 10
}
```

**응답:**
```json
{
  "id": 1,
  "quizId": 1,
  "vocabId": 1,
  "type": "OX",
  "stem": "Apple의 뜻은 사과이다.",
  "correctAnswer": "O",
  "explanation": "Apple은 사과를 의미합니다.",
  "points": 10,
  "createdAt": "2025-08-20T17:55:02.566479",
  "updatedAt": "2025-08-20T17:55:02.566479"
}
```

### 2. 퀴즈별 문제 조회
**GET** `/api/questions/quiz/{quizId}`

### 3. 특정 문제 조회
**GET** `/api/questions/{id}`

### 4. 문제 수정
**PUT** `/api/questions/{id}`

```json
{
  "stem": "수정된 문제 내용",
  "points": 15
}
```

### 5. 문제 삭제
**DELETE** `/api/questions/{id}`

---

## 🎯 퀴즈 응시 API

### 1. 학생이 응시 가능한 퀴즈 목록
**GET** `/api/quiz-taking/available/{studentId}`

### 2. 퀴즈 응시 시작
**POST** `/api/quiz-taking/start`

```json
{
  "quizId": 1,
  "studentId": 1
}
```

**응답:**
```json
{
  "quizId": 1,
  "studentId": 1,
  "quizTitle": "기초 영단어 퀴즈",
  "studentName": "김학생",
  "status": "IN_PROGRESS",
  "startedAt": "2025-08-20T17:55:02.566479"
}
```

### 3. 문제별 답안 제출
**POST** `/api/quiz-taking/answer`

```json
{
  "questionId": 1,
  "studentId": 1,
  "answer": "O"
}
```

**응답:**
```json
{
  "questionId": 1,
  "studentId": 1,
  "answer": "O",
  "isCorrect": true,
  "nextQuestionId": 2,
  "explanation": "Apple은 사과를 의미합니다."
}
```

### 4. 퀴즈 제출 완료
**POST** `/api/quiz-taking/submit`

```json
{
  "quizId": 1,
  "studentId": 1
}
```

### 5. 학생의 퀴즈 응시 결과 조회
**GET** `/api/quiz-taking/results/{studentId}`

### 6. 특정 퀴즈 응시 결과 조회
**GET** `/api/quiz-taking/result/{quizId}/{studentId}`

---

## 📊 데이터 타입 정보

### UserType
- `STUDENT`: 학생
- `TEACHER`: 교사

### UserStatus
- `ACTIVE`: 활성
- `INACTIVE`: 비활성
- `SUSPENDED`: 정지

### QuestionType
- `OX`: O/X 문제
- `SHORT`: 단답형
- `MULTIPLE`: 객관식

### QuizPerStudentStatus
- `ASSIGNED`: 할당됨
- `IN_PROGRESS`: 진행중
- `SUBMITTED`: 제출완료
- `LATE`: 지각제출

---

## 🚨 에러 응답 형식

```json
{
  "timestamp": "2025-08-20T17:55:02.566479",
  "status": 400,
  "error": "Bad Request",
  "message": "에러 메시지",
  "path": "/api/users",
  "details": {
    "fieldName": "상세 에러 정보"
  }
}
```

---

## 💡 사용 팁

1. **회원가입 시 userType 필수**: `STUDENT` 또는 `TEACHER`
2. **퀴즈 생성은 교사만 가능**: `createdBy`에 교사 ID 입력
3. **시간 형식**: ISO 8601 형식 사용 (`2025-08-21T09:00:00`)
4. **문제 유형**: `OX`, `SHORT`, `MULTIPLE` 중 선택
5. **모든 제약조건 완화됨**: null 값 허용, 최소 유효성 검사만 적용 