package com.empresa.core.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ExtendWith(MockitoExtension.class)
class ExceptionMVCHandlerTest {
    private ExceptionMVCHandler service;

    @BeforeEach
    void setUp() {
        this.service = new ExceptionMVCHandler();
    }

    @Test
    void handleException() throws Exception {
        ResponseEntity<Object> responseEntity = this.service.handleExceptions(new RuntimeException(), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    void handlerWebClientResponseException() {
        ResponseEntity<Object> responseEntity = this.service.handlerWebClientResponseException(new WebClientResponseException(500, "", new HttpHeaders(), "".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handlerServiceException() {
        ResponseEntity<Object> responseEntity = this.service.handlerServiceException(new ServiceException("500", ""));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Test
    void handlerServiceException_extra_code() {
        ResponseEntity<Object> responseEntity = this.service.handlerServiceException(new ServiceException("500", "", HttpStatus.CONFLICT));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
    @Test
    void handlerServiceException_not_found() {
        ResponseEntity<Object> responseEntity = this.service.handlerServiceException(new NotFoundServiceException());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void handlerWebClientRequestException() throws URISyntaxException {
        ResponseEntity<Object> responseEntity = this.service.handlerWebClientRequestException(new WebClientRequestException(new RuntimeException(), HttpMethod.POST, new URI("http://localhost:8080"), new HttpHeaders()));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleHandlerMethodValidationException() {
        ResponseEntity<Object> responseEntity = this.service.handleHandlerMethodValidationException(new HandlerMethodValidationException(mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleErrorResponseException() {
        ResponseEntity<Object> responseEntity = this.service.handleErrorResponseException(new ErrorResponseException(HttpStatus.BAD_REQUEST), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMethodValidationException() {
        ResponseEntity<Object> responseEntity = this.service.handleMethodValidationException(new MethodValidationException(mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHttpRequestMethodNotSupported() {
        ResponseEntity<Object> responseEntity = this.service.handleHttpRequestMethodNotSupported(new HttpRequestMethodNotSupportedException("GET"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHttpMediaTypeNotSupported() {
        ResponseEntity<Object> responseEntity = this.service.handleHttpMediaTypeNotSupported(new HttpMediaTypeNotSupportedException("GET"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHttpMediaTypeNotAcceptable() {
        ResponseEntity<Object> responseEntity = this.service.handleHttpMediaTypeNotAcceptable(new HttpMediaTypeNotAcceptableException("Hell"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMissingPathVariable() {
        ResponseEntity<Object> responseEntity = this.service.handleMissingPathVariable(new MissingPathVariableException("AA", mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMissingServletRequestParameter() {
        ResponseEntity<Object> responseEntity = this.service.handleMissingServletRequestParameter(new MissingServletRequestParameterException("aa", "aa"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMissingServletRequestPart() {
        ResponseEntity<Object> responseEntity = this.service.handleMissingServletRequestPart(new MissingServletRequestPartException("Hello"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleServletRequestBindingException() {
        ResponseEntity<Object> responseEntity = this.service.handleServletRequestBindingException(new ServletRequestBindingException("hello"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMethodArgumentNotValid() {
        ResponseEntity<Object> responseEntity = this.service.handleMethodArgumentNotValid(new MethodArgumentNotValidException(mock(), mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleNoHandlerFoundException() {
        ResponseEntity<Object> responseEntity = this.service.handleNoHandlerFoundException(new NoHandlerFoundException("", "", new HttpHeaders()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleNoResourceFoundException() {
        ResponseEntity<Object> responseEntity = this.service.handleNoResourceFoundException(new NoResourceFoundException(HttpMethod.POST, "Hell"), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleAsyncRequestTimeoutException() {
        ResponseEntity<Object> responseEntity = this.service.handleAsyncRequestTimeoutException(new AsyncRequestTimeoutException(), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMaxUploadSizeExceededException() {
        ResponseEntity<Object> responseEntity = this.service.handleMaxUploadSizeExceededException(new MaxUploadSizeExceededException(1L), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleConversionNotSupported() {
        ResponseEntity<Object> responseEntity = this.service.handleConversionNotSupported(new ConversionNotSupportedException(mock(), Object.class, mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleTypeMismatch() {
        ResponseEntity<Object> responseEntity = this.service.handleTypeMismatch(new TypeMismatchException(mock(), Object.class), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHttpMessageNotReadable() {
        ResponseEntity<Object> responseEntity = this.service.handleHttpMessageNotReadable(new HttpMessageNotReadableException("", new RuntimeException(), mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHttpMessageNotWritable() {
        ResponseEntity<Object> responseEntity = this.service.handleHttpMessageNotWritable(new HttpMessageNotWritableException("mock()", mock()), new HttpHeaders(), HttpStatus.BAD_REQUEST, mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleAsyncRequestNotUsableException() {
        ResponseEntity<Object> responseEntity = this.service.handleAsyncRequestNotUsableException(new AsyncRequestNotUsableException("mock()"), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}