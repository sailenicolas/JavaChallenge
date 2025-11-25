package com.empresa.core.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsatisfiedRequestParameterException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Mono;

public class ExceptionBaseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handlerWebClientResponseException(WebClientResponseException e){
        e.printStackTrace();
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getResponseBodyAsString());
    }
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handlerWebClientResponseException(WebClientRequestException e){
        return ResponseEntity.status(500).body(new ApiError());
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMethodNotAllowedException(MethodNotAllowedException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        ex.printStackTrace();
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Method Value invalid")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleNotAcceptableStatusException(NotAcceptableStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Not Acceptable")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleUnsupportedMediaTypeStatusException(UnsupportedMediaTypeStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Unsupported Media Type")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMissingRequestValueException(MissingRequestValueException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Missing Value")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleUnsatisfiedRequestParameterException(UnsatisfiedRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Missing Parameter")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Bind Exception")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Validation Failed")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerWebInputException(ServerWebInputException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Input Validation")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleResponseStatusException(ResponseStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Response Status")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerErrorException(ServerErrorException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Server Error")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Error Response Exception")));
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMethodValidationException(MethodValidationException ex, HttpStatus status, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.status(status).body(new ApiError(status, "Input Validation")));
    }
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Object>> handlerException(Exception e){
        return Mono.just(ResponseEntity.status(500).body(new ApiError()));
    }

}
