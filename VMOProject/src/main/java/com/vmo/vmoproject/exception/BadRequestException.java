package com.vmo.vmoproject.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {
    private List<Errors> errors;

    public List<Errors> getErrors() {
        return errors;
    }

    public BadRequestException(List<Errors> errors) {
        this.errors = errors;
    }
}
