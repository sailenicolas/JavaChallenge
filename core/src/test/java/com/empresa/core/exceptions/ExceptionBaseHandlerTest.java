package com.empresa.core.exceptions;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.empresa.core.controllers.CrudController;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.MissingRequestValueException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsatisfiedRequestParameterException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import org.springframework.web.server.adapter.DefaultServerWebExchange;

@ExtendWith(MockitoExtension.class)
class ExceptionBaseHandlerTest {

    private ExceptionBaseHandler service;

    @BeforeEach
    void setUp() {
        this.service = new ExceptionBaseHandler();
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
    void testHandlerWebClientResponseException() throws URISyntaxException {
        ResponseEntity<Object> responseEntity = this.service.handlerWebClientResponseException(new WebClientRequestException(new RuntimeException(), HttpMethod.POST, new URI("http://localhost:8080"), new HttpHeaders()));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleMethodNotAllowedException() {
        ResponseEntity<ApiError> responseEntity = this.service.handleMethodNotAllowedException(new MethodNotAllowedException(HttpMethod.POST, List.of(HttpMethod.GET)), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void handleNotAcceptableStatusException() {
        ResponseEntity<Object> responseEntity = this.service.handleNotAcceptableStatusException(new NotAcceptableStatusException("hello"), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void handleUnsupportedMediaTypeStatusException() {
        ResponseEntity<Object> responseEntity = this.service.handleUnsupportedMediaTypeStatusException(new UnsupportedMediaTypeStatusException(""), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    void handleMissingRequestValueException() {
        ResponseEntity<Object> responseEntity = this.service.handleMissingRequestValueException(new MissingRequestValueException("", Object.class, "", mock()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleUnsatisfiedRequestParameterException() {
        ResponseEntity<Object> responseEntity = this.service.handleUnsatisfiedRequestParameterException(new UnsatisfiedRequestParameterException(new ArrayList<>(), new HttpHeaders()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleWebExchangeBindException() {
        ResponseEntity<Object> responseEntity = this.service.handleWebExchangeBindException(new WebExchangeBindException(mock(), mock()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleHandlerMethodValidationException() {
        ResponseEntity<Object> responseEntity = this.service.handleHandlerMethodValidationException(new HandlerMethodValidationException(mock()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleServerWebInputException() {
        ResponseEntity<Object> responseEntity = this.service.handleServerWebInputException(new ServerWebInputException("hh"), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleResponseStatusException() {
        ResponseEntity<Object> responseEntity = this.service.handleResponseStatusException(new ResponseStatusException(HttpStatus.BAD_REQUEST, ""), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleServerErrorException() {
        ResponseEntity<Object> responseEntity = this.service.handleServerErrorException(new ServerErrorException("BAD", new RuntimeException()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleErrorResponseException() {
        ResponseEntity<Object> responseEntity = this.service.handleErrorResponseException(new ErrorResponseException(HttpStatus.BAD_REQUEST), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleMethodValidationException() {
        ResponseEntity<Object> responseEntity = this.service.handleMethodValidationException(new MethodValidationException(mock()), mock());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handlerException() {
        ResponseEntity<Object> responseEntity = this.service.handlerException(new UnsatisfiedRequestParameterException(new ArrayList<>(), new HttpHeaders()));
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}