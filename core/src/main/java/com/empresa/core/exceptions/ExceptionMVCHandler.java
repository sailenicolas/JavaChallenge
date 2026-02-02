package com.empresa.core.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;


public class ExceptionMVCHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handlerServiceException(ServiceException ex) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(ex.getHttpStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiError(ex));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiError());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Object> handlerWebClientResponseException(WebClientResponseException ex) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getResponseBodyAsString());
    }
    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<Object> handlerWebClientRequestException(WebClientRequestException ex) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(500).body(new ApiError("Error Call", "Call not Made"));
    }
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getMethod(), "Not supported")));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getContentType(), "Not supported")));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getHeaders().getContentType(), "Not Accepted")));
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s".formatted("Missing Path Variable")));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getParameterName(), "Missing parameter")));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getRequestPartName(), "Missing parameter")));
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).headers(headers).body(new ApiError("Error Call", "%s: %s".formatted(ex.getBody().getDetail(), "Binding Exception")));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        Set<String> collect = ex.getFieldErrors().parallelStream()
                .map(o -> "%s: %s".formatted(o.getField(), o.getDefaultMessage())).collect(Collectors.toSet());
        ApiError body = new ApiError();
        body.setMessage("Validation Error Call");
        body.setErrors(collect.parallelStream().toList());
        return ResponseEntity.status(status).headers(headers).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        Set<String> collect = ex.getParameterValidationResults().parallelStream()
                .map(o -> "%s: %s".formatted(o.getMethodParameter(), o.getResolvableErrors())).collect(Collectors.toSet());
        ApiError body = new ApiError();
        body.setMessage("Validation Error Call");
        body.setErrors(collect.parallelStream().toList());
        return ResponseEntity.status(status).headers(headers).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Not Found"));
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Not Found"));
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Timeout"));
    }

    @Override
    protected ResponseEntity<Object> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.debug(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Internal Error"));
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Size Exceeded"));
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Internal Error"));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Type unexpected"));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Message Not Readable"));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Message Not Writable"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodValidationException(MethodValidationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(new ApiError("Error Call", "Validation Method"));
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestNotUsableException(AsyncRequestNotUsableException ex, WebRequest request) {
        logger.error(" ERROR {}, Type {}", ExceptionUtils.getRootCauseMessage(ex.getCause()), ExceptionUtils.getMessage(ex));
        return ResponseEntity.status(500).body(new ApiError());
    }
}
