package com.empresa.api.exceptions;

import com.empresa.core.exceptions.ApiError;
import com.empresa.core.exceptions.ExceptionMVCHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

@RestControllerAdvice
public class ExceptionAdviceHandler extends ExceptionMVCHandler {
}
