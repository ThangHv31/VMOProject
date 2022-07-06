package com.vmo.vmoproject.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private List<Errors> errors;

    public BadRequestException(String message) {
        super(message);
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }

    public BadRequestException(List<Errors> errors) {
        this.errors = errors;
    }
}
