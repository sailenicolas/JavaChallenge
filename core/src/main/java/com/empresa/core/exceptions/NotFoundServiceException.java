package com.empresa.core.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundServiceException extends ServiceException {
    public NotFoundServiceException() {
        super("Not Found", "The id was not found", HttpStatus.NOT_FOUND);
    }
}
