# 정보공유 게시판

Spring Boot로 만든 게시판 웹 프로젝트입니다. 회원가입/로그인, 게시글·댓글 CRUD, 카테고리, 검색, 페이징을 구현했습니다.

## 기술 스택

- Java 21, Spring Boot 4.0.7, Gradle
- MyBatis, MySQL 8
- Thymeleaf (서버사이드 렌더링)
- BCrypt (spring-security-crypto, 비밀번호 해싱)

## 주요 기능

- 회원가입 / 로그인 / 로그아웃 (HttpSession 기반, 비밀번호는 BCrypt로 저장)
- 게시글 작성 / 조회 / 수정 / 삭제 (본인 글만 수정·삭제 가능)
- 댓글 작성 / 삭제 (본인 댓글만 삭제 가능)
- 카테고리 20개 분류
- 검색 (전체 / 제목 / 작성자 / 내용, 카테고리와 함께 검색 가능)
- 페이징 (페이지 번호 10개 단위로 묶어서 표시)
- 조회수

## 구조

```
Controller (화면용 / REST용) → Service → Mapper(MyBatis) → MySQL
```

- `BoardViewController`, `UserViewController`: Thymeleaf 화면 처리
- `BoardController`, `CommentController`: `/api/**` REST API
- 두 종류의 컨트롤러가 같은 Service, Mapper를 같이 씁니다.
- REST API도 로그인 여부와 작성자 본인 여부를 세션으로 확인합니다. (비로그인 401, 본인 아님 403)
- `GlobalControllerAdvice`에서 로그인 사용자 정보를 모든 화면에 넘겨줍니다.

## 테이블

user, board, comment 3개이고, 생성 쿼리는 `sql/schema.sql`에 있습니다.

## 실행 방법

1. MySQL에서 `sql/schema.sql`을 실행합니다.
2. `src/main/resources/application-example.properties`를 `application.properties`로 복사하고 DB 비밀번호를 채웁니다.
3. 실행합니다.

```
./gradlew bootRun
```

4. 브라우저에서 http://localhost:8080 으로 접속합니다.

## 아쉬운 점 / 앞으로 할 것

- Service, Controller 단위 테스트는 작성했지만(Mockito, MockMvc), DB까지 연결해서 보는 Mapper 테스트는 아직 없습니다.
- Spring Security 없이 세션 값을 직접 비교하는 방식이라 CSRF 방어는 넣지 않았습니다.
- 배포는 아직 안 했습니다.
