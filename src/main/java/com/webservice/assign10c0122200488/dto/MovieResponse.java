package com.webservice.assign10c0122200488.dto;

import com.webservice.assign10c0122200488.domain.Movie;

public record MovieResponse(
        Long id,
        String title,
        String director,
        String category,
        Integer releaseYear,
        Double rating
) {
    public static MovieResponse from(Movie movie){
        return new MovieResponse(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getCategory(), movie.getReleaseYear(), movie.getRating());
    }
}
