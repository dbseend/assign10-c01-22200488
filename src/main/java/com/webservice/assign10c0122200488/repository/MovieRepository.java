package com.webservice.assign10c0122200488.repository;

import com.webservice.assign10c0122200488.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Q4. JpaRepository에서 기본 제공하는 CRUD 메서드를 5개 이상 찾아 메서드명과 역할을 주석으로 작성하세요.
1. save(S entity): 새로운 엔티티를 데이터베이스에 저장(Insert)하거나, 이미 존재하는 엔티티의 변경 사항을 수정(Update)
2. findById(ID id): 식별자(ID)를 기준으로 데이터베이스에서 특정 엔티티 한 건을 조회(Select)
3. findAll(): 데이터베이스 테이블에 저장된 모든 엔티티 목록을 조회(Select)
4. deleteById(ID id): 식별자(ID)를 기준으로 데이터베이스에서 특정 데이터 한 건을 삭제(Delete)
5. count(): 데이터베이스 테이블에 저장된 전체 데이터(행)의 개수를 반환(Select Count)
*/

/*
Q5. findById()의 반환 타입이 Optional인 이유는 무엇인가?
- Id에 해당하는 조회 결과가 없을 때 발생할 수 있는 NullPointerException(NPE)을 방지하고, 값이 없는 상황을 안전하고 명확하게 처리하기 위함
*/

/*
Q6. JpaRepository에서 기본 제공하는 메서드와 개발자가 직접 작성하는 Query Method의 차이는 무엇인가?
- 기본 메소드: 공통적이고 단순한 CRUD 기능을 제공하며, 별도의 선언 없이 즉시 호출하여 사용 가능
- Query Method: • 비즈니스 로직에 필요한 복잡한 조건이나 필터링이 필요할 때 사용하며, 인터페이스에 정해진 메소드 명명 규칙에 맞춰 메서드를 직접 작성 필요
 */

/*
Q3. JpaRepository<Entity, Long>에서 두 타입은 각각 무엇을 의미하는가?
- JpaRepository<Movie, Long>: Movie는 JPA에서 관리할 Entity, Long은 Movie Entity의 PK타입을 의미
*/
public interface MovieRepository extends JpaRepository<Movie, Long> {
//  1. 단일 조건 검색 1개 이상
    List<Movie> findByCategory(String category);

//  2. 복합 조건 검색(And, Or, Between 등) 1개 이상
    List<Movie> findByReleaseYearGreaterThanEqualAndRatingGreaterThanEqual(Integer releaseYear, Double rating);

//  3. 정렬 기능(OrderBy, Asc, Desc)을 포함한 검색 1개 이상
    List<Movie> findAllByOrderByReleaseYearDesc();
}
