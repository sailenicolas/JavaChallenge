package com.empresa.pos.exceptions;

import com.empresa.core.exceptions.ApiError;
import com.empresa.core.exceptions.ExceptionBaseHandler;
import com.empresa.core.exceptions.ExceptionMVCHandler;
import com.empresa.core.exceptions.ServiceException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandler extends ExceptionMVCHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handlerServiceException(ConstraintViolationException e){
        e.printStackTrace();
        return ResponseEntity.status(400)
                .body(new ApiError());
    }
}
