package com.vmo.vmoproject.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    List<Errors> errorsList;

    public ErrorResponse(HttpStatus status, List<Errors> errorsList) {
        this.status = status;
        this.errorsList = errorsList;
    }
}
