# 🎯 영어학습 플랫폼 백엔드 API

## 📋 프로젝트 개요

영어학습 플랫폼의 백엔드 API 서버입니다. 교사와 학생이 퀴즈를 생성하고 응시할 수 있는 RESTful API를 제공합니다.

### 🎯 프로젝트 비전 및 목표
- **교사용**: 퀴즈 생성, 문제 관리, 학생 성적 분석
- **학생용**: 퀴즈 응시, 실시간 채점, 성적 확인
- **확장성**: AI 기능 연동을 위한 확장 가능한 아키텍처

## 🚀 주요 기능

### 👨‍🏫 교사용 기능
- **퀴즈 관리**: 퀴즈 생성, 수정, 삭제
- **문제 관리**: O/X, 객관식, 단답형 문제 생성
- **단어장 관리**: 영단어 및 한국어 뜻 등록
- **성적 분석**: 학생별 퀴즈 응시 결과 조회

### 👨‍🎓 학생용 기능
- **퀴즈 응시**: 실시간 문제별 채점
- **성적 확인**: 개인별 퀴즈 결과 조회
- **재시도**: 오답 문제 재도전 기능

## 🏗️ 애플리케이션 설계 및 구조

### 📊 DB 모델링 (ERD)
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    users    │    │   teacher   │    │   student   │
│ (supertype) │◄───┤ (subtype)   │    │ (subtype)   │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       │                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       │            │    quiz     │            │
       │            │             │            │
       │            └─────────────┘            │
       │                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       │            │  question   │            │
       │            │             │            │
       │            └─────────────┘            │
       │                   │                   │
       │                   ▼                   │
       │            ┌─────────────┐            │
       │            │    vocab    │            │
       │            │             │            │
       │            └─────────────┘            │
       │                   │                   │
       │                   │                   │
       ▼                   │                   ▼
┌─────────────┐            │            ┌─────────────┐
│quiz_per_    │            │            │response_per_│
│student      │            │            │question     │
│(composite   │            │            │             │
│ PK)         │            │            └─────────────┘
└─────────────┘            │
                           │
                           ▼
                   ┌─────────────┐
                   │  question   │
                   │             │
                   └─────────────┘
```

### 🔧 백엔드 아키텍처

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
├─────────────────────────────────────────────────────────────┤
│  Controller Layer (REST API)                                │
│  ├── UserController         ├── QuizController              │
│  ├── VocabController        ├── QuestionController          │
│  └── QuizTakingController   └── GlobalExceptionHandler      │
├─────────────────────────────────────────────────────────────┤
│                    Business Logic Layer                      │
├─────────────────────────────────────────────────────────────┤
│  Service Layer (Interface + Implementation)                 │
│  ├── UserServiceInterface   ├── QuizServiceInterface        │
│  ├── VocabServiceInterface  ├── QuestionServiceInterface    │
│  └── QuizTakingServiceInterface                            │
├─────────────────────────────────────────────────────────────┤
│                    Data Access Layer                         │
├─────────────────────────────────────────────────────────────┤
│  Repository Layer (Spring Data JPA)                         │
│  ├── UserRepository         ├── QuizRepository              │
│  ├── VocabRepository        ├── QuestionRepository          │
│  └── QuizPerStudentRepository                              │
├─────────────────────────────────────────────────────────────┤
│                    Domain Layer                              │
├─────────────────────────────────────────────────────────────┤
│  Entity Layer (JPA Entities)                                │
│  ├── User (Supertype)       ├── Quiz                        │
│  ├── Student/Teacher        ├── Question                    │
│  └── Vocab                  └── QuizPerStudent              │
├─────────────────────────────────────────────────────────────┤
│                    Database Layer                            │
├─────────────────────────────────────────────────────────────┤
│  PostgreSQL Database (Docker Container)                     │
└─────────────────────────────────────────────────────────────┘
```

## 🛠️ 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.4
- **Language**: Java 17
- **ORM**: Spring Data JPA (Hibernate)
- **Database**: PostgreSQL 15
- **Build Tool**: Gradle
- **Validation**: Bean Validation
- **Security**: Spring Security (개발용 CORS 설정)
- **Documentation**: API Documentation (README)

### Infrastructure
- **Container**: Docker & Docker Compose
- **Version Control**: Git
- **IDE**: IntelliJ IDEA / VS Code

### External APIs (계획)
- **AI/LLM**: OpenAI GPT API (문제 자동 생성)
- **TTS**: Google Text-to-Speech API (받아쓰기 음성)
- **RAG**: Retrieval-Augmented Generation (지식베이스)

## 📁 프로젝트 구조

```
backendapp/
├── src/main/java/com/kt/backendapp/
│   ├── config/                 # 설정 클래스
│   │   └── SecurityConfig.java
│   ├── controller/             # REST API 컨트롤러
│   │   ├── UserController.java
│   │   ├── VocabController.java
│   │   ├── QuizController.java
│   │   ├── QuestionController.java
│   │   └── QuizTakingController.java
│   ├── service/               # 비즈니스 로직 (인터페이스 + 구현)
│   │   ├── UserServiceInterface.java
│   │   ├── UserService.java
│   │   ├── VocabServiceInterface.java
│   │   ├── VocabService.java
│   │   ├── QuizServiceInterface.java
│   │   ├── QuizService.java
│   │   ├── QuestionServiceInterface.java
│   │   ├── QuestionService.java
│   │   ├── QuizTakingServiceInterface.java
│   │   └── QuizTakingService.java
│   ├── domain/               # 엔티티 및 리포지토리
│   │   ├── common/
│   │   │   └── BaseTimeEntity.java
│   │   ├── user/
│   │   │   ├── User.java (Supertype)
│   │   │   ├── Student.java (Subtype)
│   │   │   ├── Teacher.java (Subtype)
│   │   │   ├── UserRepository.java
│   │   │   ├── StudentRepository.java
│   │   │   └── TeacherRepository.java
│   │   ├── vocab/
│   │   │   ├── Vocab.java
│   │   │   └── VocabRepository.java
│   │   ├── quiz/
│   │   │   ├── Quiz.java
│   │   │   ├── QuizRepository.java
│   │   │   ├── QuizPerStudent.java (Composite PK)
│   │   │   ├── QuizPerStudentRepository.java
│   │   │   ├── ResponsePerQuestion.java
│   │   │   └── ResponsePerQuestionRepository.java
│   │   └── question/
│   │       ├── Question.java
│   │       ├── QuestionRepository.java
│   │       └── QuestionType.java
│   ├── dto/                  # 데이터 전송 객체
│   │   ├── common/
│   │   │   └── ErrorResponse.java
│   │   ├── user/
│   │   │   ├── UserRequestDto.java
│   │   │   ├── UserResponseDto.java
│   │   │   ├── UserUpdateDto.java
│   │   │   ├── UserRegistrationDto.java
│   │   │   ├── StudentRequestDto.java
│   │   │   ├── StudentResponseDto.java
│   │   │   ├── TeacherRequestDto.java
│   │   │   └── TeacherResponseDto.java
│   │   ├── vocab/
│   │   │   ├── VocabCreateDto.java
│   │   │   ├── VocabResponseDto.java
│   │   │   └── VocabUpdateDto.java
│   │   ├── quiz/
│   │   │   ├── QuizCreateDto.java
│   │   │   ├── QuizResponseDto.java
│   │   │   └── QuizUpdateDto.java
│   │   ├── question/
│   │   │   ├── QuestionCreateDto.java
│   │   │   ├── QuestionResponseDto.java
│   │   │   └── QuestionUpdateDto.java
│   │   └── quiztaking/
│   │       ├── QuizTakingResponseDto.java
│   │       ├── QuestionAnswerDto.java
│   │       └── QuestionAnswerResponseDto.java
│   └── exception/            # 예외 처리
│       ├── GlobalExceptionHandler.java
│       ├── UserNotFoundException.java
│       ├── QuizNotFoundException.java
│       ├── QuestionNotFoundException.java
│       └── VocabNotFoundException.java
├── src/main/resources/
│   ├── application.yml       # 애플리케이션 설정
│   └── ddl.sql              # 데이터베이스 스키마
├── postgresql-compose.yml   # Docker Compose 설정
├── build.gradle            # Gradle 의존성 관리
└── API_DOCUMENTATION.md    # API 문서
```

## 🚀 실행 방법

### 1. 환경 설정
```bash
# Java 17 설치 확인
java -version

# Docker 설치 확인
docker --version
```

### 2. 데이터베이스 실행
```bash
# PostgreSQL 컨테이너 실행
docker-compose -f postgresql-compose.yml up -d

# 데이터베이스 연결 확인
docker exec -it kt-postgres psql -U kt -d mydb
```

### 3. 애플리케이션 실행
```bash
# 프로젝트 디렉토리 이동
cd backendapp

# 애플리케이션 실행
./gradlew bootRun
```

### 4. API 테스트
```bash
# 서버 상태 확인
curl http://localhost:8080/api/users

# 학생 회원가입
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "userInfo": {
      "email": "student1@test.com",
      "password": "password123",
      "name": "김학생"
    },
    "userType": "STUDENT",
    "level": "초급"
  }'
```

## 📚 API 문서

자세한 API 문서는 [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)를 참조하세요.

### 주요 엔드포인트
- **사용자 관리**: `/api/users/*`
- **단어장 관리**: `/api/vocabs/*`
- **퀴즈 관리**: `/api/quizzes/*`
- **문제 관리**: `/api/questions/*`
- **퀴즈 응시**: `/api/quiz-taking/*`

## 🔧 주요 설계 패턴

### 1. Super-Subtype 패턴
- `User` (Supertype) → `Student`, `Teacher` (Subtype)
- 상속을 통한 역할별 데이터 분리

### 2. Interface-Service 패턴
- 모든 Service 클래스에 Interface 구현
- 의존성 주입 및 테스트 용이성 확보

### 3. DTO 패턴
- Request/Response DTO 분리
- 계층간 데이터 전송 객체 명확화

### 4. Repository 패턴
- Spring Data JPA 활용
- 데이터 접근 계층 추상화

### 5. Global Exception Handling
- 중앙화된 예외 처리
- 일관된 에러 응답 형식

## 🎯 핵심 기능 구현

### 1. 실시간 퀴즈 채점
- 문제별 즉시 정답 확인
- 재시도 기능 지원
- 점수 실시간 계산

### 2. 복합키 처리
- `QuizPerStudent` 엔티티의 복합 PK
- 학생별 퀴즈 응시 기록 관리

### 3. 유효성 검사
- Bean Validation 활용
- 입력 데이터 검증 강화

### 4. 확장 가능한 구조
- AI 기능 연동을 위한 인터페이스 설계
- 외부 API 연동 준비

## 🔮 향후 계획

### Phase 1: AI 기능 연동
- OpenAI GPT API 연동 (문제 자동 생성)
- Google TTS API 연동 (받아쓰기 음성)

### Phase 2: 고급 기능
- RAG 시스템 구축
- 학생 성적 분석 대시보드
- 개인화 학습 추천

### Phase 3: 성능 최적화
- 캐싱 전략 적용
- 데이터베이스 인덱스 최적화
- API 응답 속도 개선

## 👥 팀원

- **Backend Developer**: [이름]
- **Frontend Developer**: [이름]
- **Project Manager**: [이름]

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

---

**English Learning Platform Backend API** 🎓
