package com.webservice.assign10c0122200488.controller;

import com.webservice.assign10c0122200488.dto.MovieRequest;
import com.webservice.assign10c0122200488.dto.MovieResponse;
import com.webservice.assign10c0122200488.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping("")
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movieRequest){
        MovieResponse movieResponse = movieService.addMovie(movieRequest);

        return ResponseEntity.created(URI.create("/api/movies/" + movieResponse.id())).body(movieResponse);
    }

    @GetMapping("")
    public List<MovieResponse> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/desc")
    public List<MovieResponse> getAllMoviesOrderByReleaseYear() {return movieService.getAllMoviesOrderByReleaseYear();}
//
//    @GetMapping("")
//    public List<MovieResponse> getMoviesByCategory(@RequestParam String category){return movieService.getMoviesByCategory(category);}
//
//    @GetMapping("")
//    public List<MovieResponse> getMoviesByReleaseYearAndRating(@RequestParam Integer releaseYear, @RequestParam Double rating){
//        return  movieService.getMoviesByReleaseYearAndRating(releaseYear,rating);}

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovieById(@PathVariable Long id, @RequestBody MovieRequest movieRequest){
        return movieService.updateMovieById(id, movieRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.noContent().build();
    }
}
