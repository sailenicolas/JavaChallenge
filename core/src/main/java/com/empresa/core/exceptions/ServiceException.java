package com.empresa.core.exceptions;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends RuntimeException{
    private String message;
    private List<String> errors;
    private HttpStatusCode httpStatusCode;
    public ServiceException(String message, String errors) {
        super(message);
        this.message = message;
        this.errors = Collections.singletonList(errors);
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ServiceException(String message, String errors, HttpStatusCode httpStatusCode) {
        super(message);
        this.message = message;
        this.errors = Collections.singletonList(errors);
        this.httpStatusCode = httpStatusCode;
    }
}
