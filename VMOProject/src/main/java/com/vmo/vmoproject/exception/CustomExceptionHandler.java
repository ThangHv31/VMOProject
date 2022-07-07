package com.vmo.vmoproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone handlerBadRequestException(BadRequestException ex, WebRequest req) {
        return new ErrorRespone(HttpStatus.BAD_REQUEST, ex.getErrors());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorRespone handlerDuplicateRecordException(MethodArgumentNotValidException ex, WebRequest req) {
        ErrorRespone errorRespone = new ErrorRespone();
        List<Errors> errorsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            Errors error = new Errors();
            error.setErrorCode(err.getCode());
            error.setErrorDetail(err.getDefaultMessage());
            errorsList.add(error);
        });
        errorRespone.setErrorsList(errorsList);
        return new ErrorRespone(HttpStatus.BAD_REQUEST, errorsList);
    }
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorRespone handlerNotFoundException(NotFoundException ex, WebRequest req) {
        return new ErrorRespone(HttpStatus.NOT_FOUND, Arrays.asList(ex.getErrors()));
    }

}
