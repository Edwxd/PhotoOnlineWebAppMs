package com.edward.photoonlineprintingwebservice.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class HttpErrorInfo {

    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus status;
    private String message;

    public HttpErrorInfo(HttpStatus status, String path,  String message) {
        timestamp = ZonedDateTime.now();
        this.path = path;
        this.status = status;
        this.message = message;
    }
}
