package com.empresa.data.exceptions;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ExtendWith(SpringExtension.class)
class ExceptionHandlerTest {
    private ExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        this.exceptionHandler = new ExceptionHandler();
    }

    @Test
    void handlerWebClientResponseException() {
        ResponseEntity<Object> responseEntity = this.exceptionHandler.handlerWebClientResponseException(new WebClientResponseException(500, "", new HttpHeaders(), "".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
    }

    @Test
    void handlerException() {
        ResponseEntity<Object> responseEntity = this.exceptionHandler.handlerException(new WebClientResponseException(500, "", new HttpHeaders(), "".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
    }
}