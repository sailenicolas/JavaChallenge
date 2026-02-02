package com.empresa.api.exceptions;

import com.empresa.core.exceptions.ApiError;
import com.empresa.core.exceptions.ExceptionMVCHandler;
import jakarta.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviceHandler extends ExceptionMVCHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<String> collect = ex.getConstraintViolations().parallelStream()
                .map(o -> "%s: %s".formatted(o.getPropertyPath().toString().split("\\.")[1], o.getMessage())).collect(Collectors.toSet());
        ApiError body = new ApiError();
        body.setMessage("Validation Error Call");
        body.setErrors(collect.parallelStream().toList());
        return ResponseEntity.status(400).body(body);
    }

}
