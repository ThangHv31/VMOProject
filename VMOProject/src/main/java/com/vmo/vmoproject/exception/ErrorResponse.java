package com.vmo.vmoproject.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private HttpStatus status;
    List<Errors> errorsList;

    public ErrorResponse(HttpStatus status, List<Errors> errorsList) {
        this.status = status;
        this.errorsList = errorsList;
    }
}
