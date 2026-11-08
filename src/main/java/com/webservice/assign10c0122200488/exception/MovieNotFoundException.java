package com.webservice.assign10c0122200488.exception;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Long id) {
        super("ID " + id + "에 해당하는 영화가 없습니다.");
    }
}
