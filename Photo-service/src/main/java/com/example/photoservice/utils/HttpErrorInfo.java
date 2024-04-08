package com.example.photoservice.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class HttpErrorInfo {

    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private String message;

    public HttpErrorInfo(HttpStatus httpStatus, String path,  String message) {
        timestamp = ZonedDateTime.now();
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
