package com.webservice.assign10c0122200488.dto;

public record MovieRequest(
        String title,
        String director,
        String category,
        Integer releaseYear,
        Double rating
) {
}
