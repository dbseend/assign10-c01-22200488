# 웹 서비스 개발 9주차 과제 - Spring Data JPA + MariaDB REST CRUD (Render 배포)

| 구분 | 링크                                                         |
| :--- |:-----------------------------------------------------------|
| **Render 배포 URL** | https://assign10-c01-22200488-1.onrender.com               |
| GitHub (Organization) | https://github.com/2026-2-WebService/assign09-c01-22200488 |
| GitHub (Personal) | https://github.com/dbseend/assign10-c01-22200488           |

---

## ① 프로젝트 소개

### 주제 및 주요 기능
주제: 영화
주요 기능
- 영화 생성
- 영화 전체 조회
- 영화 단건 조회
- 영화 수정
- 영화 삭제
### Entity 필드 구조 (`movies`)

| 필드 | 타입 | Column 설정 |
| :--- | :--- | :--- |
| `id` | `Long` | `@Id` `@GeneratedValue(IDENTITY)` |
| `title` | `String` | `nullable = false`, `length = 200` |
| `director` | `String` | `nullable = false`, `length = 100` |
| `category` | `String` | `nullable = false`, `length = 50` |
| `releaseYear` | `Integer` | `nullable = false` |
| `rating` | `Double` | `nullable = false` |

### 프로젝트 구조 및 개발환경
```text
src/main/java/com/webservice/assign09c0122200488
├── Assign09C0122200488Application.java
├── controller/MovieController.java
├── service/MovieService.java
├── repository/MovieRepository.java
├── domain/Movie.java
├── dto/MovieRequest.java, MovieResponse.java
└── exception/MovieNotFoundException.java, GlobalExceptionHandler.java
```
구조: Controller → Service → JpaRepository → Hibernate → MariaDB

| 항목 | 내용 |
| :--- | :--- |
| JDK / Build | 17 / Gradle |
| Framework | Spring Boot 4.1.2, Spring Data JPA |
| DB | MariaDB (`walab.handong.edu:3306`) |
| 배포 | Docker + Render |

---

## ② Database SQL Practice

### 기존 Table 조회 및 데이터 추가
기존 Table: `product` (7주차 실습 Table)

추가 전 조회: 4건 (`product_id` 1, 2, 4, 5)
```sql
SELECT * FROM product;
```
![select-before](images/10-product-select-before.png)

데이터 추가
```sql
INSERT INTO product(name, price, category, stock)
VALUES ('나이키 팬텀 GX 2', 189000, '축구화/풋살화', 30);
```
![insert](images/11-product-insert.png)

추가 후 조회: 5건 (`product_id` 6 추가)

![select-after](images/12-product-select-after.png)

### 새 Table 생성 (`movie_sql`)
![create](images/01-movie-sql-create.png)

### INSERT / UPDATE / DELETE / SELECT

**① INSERT** 
![insert](images/02-movie-sql-insert.png)
![select-after-insert](images/03-movie-sql-select-after-insert.png)

**② UPDATE**
![update](images/04-movie-sql-update.png)
![select-after-update](images/05-movie-sql-select-after-update.png)

**③ DELETE**
![delete](images/06-movie-sql-delete.png)

**④ SELECT**
![select-all](images/07-movie-sql-select-after-delete.png)
![select-columns](images/08-movie-sql-select-columns.png)
![select-genre](images/09-movie-sql-select-genre-sf.png)

---

## ③ Spring Data JPA 구현

### Entity 및 JpaRepository 설명
- Entity: 데이터베이스의 테이블과 1:1로 매핑되는 자바 객체
- JpaRepository: Entity에 대한 기본적인 CRUD(생성, 조회, 수정, 삭제) 기능을 제공하는 인터페이스

### 사용한 Annotation의 역할
| Annotation | 역할                                  |
| :--- |:------------------------------------|
| `@Entity` | 데이터베이스 테이블과 매핑될 클래스임을 지정            |
| `@Table` | 엔티티와 매핑할 데이터베이스 테이블의 이름을 명시         |
| `@Id` | 테이블의 기본키(Primary Key, PK)가 될 필드를 지정 |
| `@GeneratedValue` | 기본키의 값을 자동으로 생성하는 전략을 설정            |
| `@Column` | 필드와 테이블의 컬럼을 매핑                     |

### REST API Endpoint
| Method | URL | 설명 | 성공 | 실패 |
| :--- | :--- | :--- | :--- | :--- |
| POST | `/api/movies` | 등록 | 201 | 400 |
| GET | `/api/movies` | 전체 조회 | 200 | |
| GET | `/api/movies/{id}` | 단건 조회 | 200 | 404 |
| PUT | `/api/movies/{id}` | 수정 | 200 | 400 / 404 |
| DELETE | `/api/movies/{id}` | 삭제 | 204 | 404 |

### 요청·응답 (Postman, 로컬)

| 기능 | 요청 | 응답 | 화면 |
| :--- | :--- | :--- | :--- |
| 등록 | `POST /api/movies` | 201 Created | ![post](images/16-postman-post.png) |
| 전체 조회 | `GET /api/movies` | 200 OK | ![get-all](images/17-postman-get-all.png) |
| 단건 조회 | `GET /api/movies/5` | 200 OK | ![get-one](images/18-postman-get-one.png) |
| 수정 | `PUT /api/movies/5` | 200 OK | ![put](images/19-postman-put.png) |
| 삭제 | `DELETE /api/movies/6` | 204 No Content | ![delete](images/20-postman-delete.png) |
| 없는 ID 조회 | `GET /api/movies/10` | 404 Not Found | ![404](images/22-postman-404.png) |
| 잘못된 입력 (`rating` -5.5) | `POST /api/movies` | 400 Bad Request | ![400](images/23-postman-400.png) |

### JPA Entity와 MariaDB Table 비교
DBeaver에서 확인한 `movies` Table (`releaseYear` → `release_year`)

![dbeaver-movies](images/24-dbeaver-movies.png)

Entity 코드 (`Movie.java`)

![entity-code](images/38-entity-code.png)

DBeaver의 `movies` Table 컬럼 구조

![dbeaver-columns](images/39-dbeaver-movies-columns.png)

| Entity | MariaDB 컬럼 |
| :--- | :--- |
| `Long id` + `IDENTITY` | `id bigint(20)`, PRI, auto_increment |
| `String title` (length 200) | `title varchar(200)` |
| `String director` (length 100) | `director varchar(100)` |
| `String category` (length 50) | `category varchar(50)` |
| `Integer releaseYear` | `release_year int(11)` |
| `Double rating` | `rating double` |

---

## ④ JPA와 SQL 비교
- `save()` / `findAll()` / `findById()` / `deleteById()` 사용 결과

| 호출 | Hibernate SQL | 화면 |
| :--- | :--- | :--- |
| `save()` (등록) | `insert into movies` | ![insert](images/16-hibernate-insert.png) |
| `findAll()` | `select ... from movies` | ![select-all](images/17-hibernate-select-all.png) |
| `findById()` | `select ... from movies where id=?` | ![select-one](images/18-hibernate-select-one.png) |
| `save()` (수정) | `update movies set ... where id=?` | ![update](images/19-hibernate-update.png) |
| `deleteById()` | `delete from movies where id=?` | ![delete](images/20-hibernate-delete.png) |
- SQL 직접 작성 방식과 JPA 방식의 차이
  - SQL: 개발자가 직접 SQL 쿼리문을 작성하고 데이터베이스를 중심으로 애플리케이션을 개발하는 방식, 직접 SQL 작성 필요
  - JPA: 자바 객체와 데이터베이스 테이블을 매핑하여, 객체 지향적인 코드 중심으로 개발하는 방식, 직접 SQL 작성 필요 없음

---

## ⑤ 배포 및 환경 설정
- 환경변수 (변수명만 기록): `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- IntelliJ 설정 방법: Run → Edit Configurations → 실행 설정의 Environment variables: DB_URL, DB_USERNAME, DB_PASSWORD 설정 → application.properties가 내용을 읽고 프로그램 실행  
실제 하드코딩 하지 않아도 환경변수를 읽고 디비를 불러와서 실행

![intellij-env](images/33-intellij-env.png)

- Render 환경변수 (Environment 메뉴, 값은 마스킹 상태)

![render-env](images/32-render-env.png)
- Render 배포 과정: 깃허브 연결, 브랜치 설정, 런타임 Docker, 환경 변수에 DB_URL, DB_USERNAME, DB_PASSWORD 설정 후 배포, 배포 확인 후 링크 통해 서버 이용
- Render Web Service (Docker, Free, `main` 브랜치, 상태 Live)

![render-service](images/21-render-service.png)
- 배포 URL CRUD 테스트 결과 (Postman, 호출 순서대로)

| 순서 | 요청 | 응답 | 화면 |
| :--- | :--- | :--- | :--- |
| 1 | `GET /api/movies` (테스트 전: id 1, 2, 4) | 200 OK | ![get-all-before](images/25-render-get-all-before.png) |
| 2 | `POST /api/movies` ("파묘", 생성 id 5) | 201 Created | ![post](images/26-render-post.png) |
| 3 | `GET /api/movies/4` | 200 OK | ![get-one-4](images/27-render-get-one-4.png) |
| 4 | `PUT /api/movies/5` (파묘 → 엘리멘탈) | 200 OK | ![put](images/28-render-put.png) |
| 5 | `GET /api/movies/5` (수정 결과 확인) | 200 OK | ![get-one-5](images/29-render-get-one-5.png) |
| 6 | `DELETE /api/movies/2` | 204 No Content | ![delete](images/30-render-delete.png) |
| 7 | `GET /api/movies` (테스트 후: id 1, 4, 5) | 200 OK | ![get-all-after](images/31-render-get-all-after.png) |
- MariaDB 데이터 저장 확인: Render URL로 `POST` 3건 등록 후 DBeaver에서 `movies` 조회

| 순서 | Render POST (201 Created) | 화면 |
| :--- | :--- | :--- |
| 1 | 오펜하이머 (id 8) | ![post-8](images/34-render-post-oppenheimer.png) |
| 2 | 한산: 용의 출현 (id 9) | ![post-9](images/35-render-post-hansan.png) |
| 3 | 듄: 파트2 (id 10) | ![post-10](images/36-render-post-dune.png) |

DBeaver `movies` 조회: id 8, 9, 10이 저장됨

![dbeaver-after-render-post](images/37-dbeaver-after-render-post.png)

- 발생한 문제와 해결 방법: 예외처리 애매하게 해서 뭐가 오류인지 정확하게 몰랐는데 Exception 해서 수정함
---

## ⑥ Weekly Report

### Key Learning
1. JPA를 통해서 직접 SQL문 작성없이 데이터베이스에 접근하는 기능들을 구현함
2. Entity, Column과 같은 어노테이션을 통해서 데이터베이스와 자바프로그램을 매핑시킴
3. Lombok의 NoArgsConstructor, Getter와 같은 어노테이션을 이용해서 불필요한 코드 작성을 줄임

### Problem & Solution
Problem: 예외처리를 구체적으로 하지 않아 예외가 발생했을 때 구체적인 오류를 찾지 못함  
Solution: 예외 코드 자세히 작성해 해결
### Code Review
```java
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
```
• Movie 엔티티와 Long 타입의 ID를 매핑하여, 별도의 구현 코드 없이 DB의 테이블과 상호작용하도록 설정
• JpaRepository를 상속받음으로써 기본적인 CRUD(저장, 조회, 수정, 삭제) 및 페이징 처리를 위한 메서드를 자동으로 제공받아 사용
### AI Usage
- 사용한 AI 도구: Claude
- 사용한 부분: 리드미 초안 작성, 예외 코드 작성
- 직접 수정·검증한 부분: 엔티티 ~ 컨트롤러까지 전체적인 코드
- AI 사용률: 10% 미만

### Reflection
실제 데이터베이스를 연결해 배포까지 진행해서 내 프로젝트를 돌려보는 경험을 할 수 있어서 좋았음
