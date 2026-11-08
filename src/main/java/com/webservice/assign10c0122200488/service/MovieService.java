package com.webservice.assign10c0122200488.service;

import com.webservice.assign10c0122200488.domain.Movie;
import com.webservice.assign10c0122200488.dto.MovieRequest;
import com.webservice.assign10c0122200488.dto.MovieResponse;
import com.webservice.assign10c0122200488.exception.MovieNotFoundException;
import com.webservice.assign10c0122200488.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public MovieResponse addMovie(MovieRequest movieRequest){
        validate(movieRequest);

        /*
        Q7. save()를 호출하면 새로운 데이터 등록과 기존 데이터 수정이 어떻게 처리되는가?
        - save() 호출 시 데이터의 신규성을 먼저 검사한 후, 신규 데이터는 새로 등록(Insert)하고 기존 데이터는 수정된 내용을 업데이트(Update) 진행
        */
        Movie newMovie = movieRepository.save(new Movie(movieRequest));

        return MovieResponse.from(newMovie);
    }

    public List<MovieResponse> getAllMovies(){
        return movieRepository.findAll().stream().map(MovieResponse::from).toList();
    }

    public MovieResponse getMovieById(Long id){
        return movieRepository.findById(id)
                .map(MovieResponse::from)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public List<MovieResponse> getMoviesByCategory(String category){
        return movieRepository.findByCategory(category)
                .stream().map(MovieResponse::from).toList();
    }

    public List<MovieResponse> getMoviesByReleaseYearAndRating(Integer releaseYear, Double rating){
        int minYear = (releaseYear != null) ? releaseYear : Integer.MIN_VALUE;
        double minRating = (rating != null) ? rating : -Double.MAX_VALUE;

        return movieRepository.findByReleaseYearGreaterThanEqualAndRatingGreaterThanEqual(minYear, minRating)
                .stream().map(MovieResponse::from).toList();
    }

    public List<MovieResponse> getAllMoviesOrderByReleaseYear(){
        return movieRepository.findAllByOrderByReleaseYearDesc().stream().map(MovieResponse::from).toList();
    }

    /*
    Q8. @Transactional과 Dirty Checking은 어떤 관계가 있는가?
	- Dirty Checking은 엔티티의 변경을 감지해 UPDATE 쿼리를 자동 실행하는 기능
	- 엔티티의 변경을 감지하려면 엔티티가 영속성 컨텍스트에 살아있어야함
	- @Transactional은 메서드 내의 데이터베이스 처리를 하나의 단위로 묶어 영속성 컨텍스트의 생명주기를 메서드가 끝날 때까지 유지해 Dirty Checking을 가능하게 함
    */
    @Transactional
    public MovieResponse updateMovieById(Long id, MovieRequest movieRequest){
        Movie targetMovie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        validate(movieRequest);

        targetMovie.update(movieRequest);

        return MovieResponse.from(targetMovie);
    }

    public void deleteMovieById(Long id){
        if(!movieRepository.existsById(id)){
            throw new MovieNotFoundException(id);
        }

        movieRepository.deleteById(id);
    }

    private void validate(MovieRequest movieRequest){
        if(movieRequest.title() == null || movieRequest.title().isBlank()){
            throw new IllegalArgumentException("제목은 반드시 입력해야 합니다.");
        }
        if(movieRequest.director() == null || movieRequest.director().isBlank()){
            throw new IllegalArgumentException("감독은 반드시 입력해야 합니다.");
        }
        if(movieRequest.category() == null || movieRequest.category().isBlank()){
            throw new IllegalArgumentException("카테고리는 반드시 입력해야 합니다.");
        }
        if(movieRequest.releaseYear() == null || movieRequest.releaseYear() < 1900 || movieRequest.releaseYear() > 2100){
            throw new IllegalArgumentException("개봉연도는 1900~2100 사이여야 합니다.");
        }
        if(movieRequest.rating() == null || movieRequest.rating() < 0 || movieRequest.rating() > 10){
            throw new IllegalArgumentException("평점은 0~10 사이여야 합니다.");
        }
    }
}
