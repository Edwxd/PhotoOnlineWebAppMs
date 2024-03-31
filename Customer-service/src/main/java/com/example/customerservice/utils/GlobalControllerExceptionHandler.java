package com.example.customerservice.utils;

import com.example.customerservice.utils.exceptions.InUseException;
import com.example.customerservice.utils.exceptions.InvalidInputException;
import com.example.customerservice.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, WebRequest request, Exception exception) {

        final String path = request.getDescription(false);
        final String message = exception.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);

        return new HttpErrorInfo(httpStatus, path, message);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public HttpErrorInfo handleNotFoundException(WebRequest request, Exception exception){
        return createHttpErrorInfo(HttpStatus.NOT_FOUND, request, exception);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InUseException.class)
    public HttpErrorInfo handleInUseException(WebRequest request, Exception exception){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, exception);
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public HttpErrorInfo handleInvalidInputException(WebRequest request, Exception exception){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, exception);
    }
}
