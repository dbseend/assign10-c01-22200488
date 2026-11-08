package com.webservice.assign10c0122200488.domain;

import com.webservice.assign10c0122200488.dto.MovieRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/* 객체와 DB 테이블 간의 매핑을 담당하는 클래스 */
@Entity
/* Movie 클래스를 데이터베이스의 movies 테이블로 매핑 */
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Movie {
    /* 테이블의 기본 키(PK)로 지정 */
    @Id
    /* 기본 키(PK) 자동 생성하도록 설정, GenerationType.IDENTITY은 MariaDB의 AUTO_INCREMENT 사용 */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String director;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Integer releaseYear;

    @Column(nullable = false)
    private Double rating;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Movie(MovieRequest movieRequest){
        this.title = movieRequest.title();
        this.director = movieRequest.director();
        this.releaseYear = movieRequest.releaseYear();
        this.category = movieRequest.category();
        this.rating = movieRequest.rating();
    }

    public void update(MovieRequest movieRequest){
        this.title = movieRequest.title();
        this.director = movieRequest.director();
        this.releaseYear = movieRequest.releaseYear();
        this.category = movieRequest.category();
        this.rating = movieRequest.rating();
    }
}
