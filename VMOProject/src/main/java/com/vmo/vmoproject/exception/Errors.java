package com.vmo.vmoproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Errors {
    private String errorCode;
    private String errorDetail;

    public Errors(String errorDetail) {
        this.errorDetail = errorDetail;
    }
}
