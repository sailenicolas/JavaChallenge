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

public class ExceptionBaseHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handlerWebClientResponseException(WebClientResponseException e){
        return ResponseEntity.status(e.getStatusCode())
                .body(e.getResponseBodyAsString());
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handlerServiceException(ServiceException e){
        return ResponseEntity.status(e.getHttpStatusCode())
                .body(new ApiError(e));
    }
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handlerWebClientResponseException(WebClientRequestException e){
        return ResponseEntity.status(500).body(new ApiError());
    }
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowedException(MethodNotAllowedException ex,  ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ApiError(HttpStatus.METHOD_NOT_ALLOWED, "Method Value invalid"));
    }

    @ExceptionHandler(NotAcceptableStatusException.class)
    protected ResponseEntity<Object> handleNotAcceptableStatusException(NotAcceptableStatusException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ApiError(HttpStatus.NOT_ACCEPTABLE, "Not Acceptable")));
    }

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    protected ResponseEntity<Object> handleUnsupportedMediaTypeStatusException(UnsupportedMediaTypeStatusException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type")));
    }

    @ExceptionHandler(MissingRequestValueException.class)
    protected ResponseEntity<Object> handleMissingRequestValueException(MissingRequestValueException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Missing Value")));
    }

    @ExceptionHandler(UnsatisfiedRequestParameterException.class)
    protected ResponseEntity<Object> handleUnsatisfiedRequestParameterException(UnsatisfiedRequestParameterException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Missing Parameter")));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    protected ResponseEntity<Object> handleWebExchangeBindException(WebExchangeBindException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Bind Exception")));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,  ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Validation Failed"));
    }

    @ExceptionHandler(ServerWebInputException.class)
    protected ResponseEntity<Object> handleServerWebInputException(ServerWebInputException ex, ServerWebExchange exchange) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Input Validation"));
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Response Status")));
    }

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<Object> handleServerErrorException(ServerErrorException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Server Error")));
    }

    @ExceptionHandler(ErrorResponseException.class)
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex,  ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Error Response Exception")));
    }

    @ExceptionHandler(MethodValidationException.class)
    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException ex, ServerWebExchange exchange) {
        return (ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, "Input Validation")));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception e){
        return (ResponseEntity.status(500).body(new ApiError()));
    }

}
