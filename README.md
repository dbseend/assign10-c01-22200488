# 웹 서비스 개발 10주차 과제: Spring Data JPA 응용 및 기능 확장

---

## ① 프로젝트 소개
### 주제 및 주요 기능
- 주제
  - 영화 목록 관리 프로그램
- 주요 기능
  - 영화 생성
  - 영화 전체 조회
  - 영화 단건 조회
  - 영화 수정
  - 영화 삭제
### GitHub & Render URL
| 구분 | 링크 |
| :--- | :--- |
| **Render 배포 URL** | https://assign10-c01-22200488.onrender.com |
| GitHub (Organization) | https://github.com/2026-2-WebService/assign09-c01-22200488 |
| GitHub (Personal) | https://github.com/dbseend/assign09-c01-22200488 |

## ② 주요 구현 내용 및 결과
### Query Method 3개: 검색 조건 및 API 실행 결과
- 단일 조건 검색: 카테고리별 조회
- 복합 조건 검색: 특정 개봉년도 이상이면서, 특정 평점 이상인 영화 조회
- 정렬 포함 검색: 최신 개봉일 순으로 전체 조회
₩
### Dirty Checking: 수정 기능 구현 및 실행 결과
- @Transactional과 Dirty Checking을 통해 save() 호출 없이 수정 기능 구현

### Auditing: 생성·수정 시간 자동 기록 결과

[//]: # (※ 소스 코드에 작성한 설명이나 주석을 README에 중복 작성할 필요는 없습니다. 주요 기능의 실행 결과를 중심으로 정리하세요.)



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
