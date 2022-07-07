package com.vmo.vmoproject.exception;

public class NotFoundException extends RuntimeException{
   private Errors errors;

    public NotFoundException(Errors errors) {
        this.errors = errors;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
