package com.vmo.vmoproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerBadRequestException(BadRequestException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getErrors());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerDuplicateRecordException(MethodArgumentNotValidException ex) {
        List<Errors> errorsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            Errors error = new Errors();
            error.setErrorCode(err.getCode());
            error.setErrorDetail(err.getDefaultMessage());
            errorsList.add(error);
        });
        return new ErrorResponse(HttpStatus.BAD_REQUEST, errorsList);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception ex) {
        Errors errors = new Errors(ex.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(errors));
    }
}
