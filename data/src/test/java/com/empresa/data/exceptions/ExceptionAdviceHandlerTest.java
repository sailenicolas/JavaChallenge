package com.empresa.data.exceptions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.exceptions.ApiError;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class ExceptionAdviceHandlerTest {
    private ExceptionAdviceHandler exceptionAdviceHandler;

    @BeforeEach
    void setUp() {
        this.exceptionAdviceHandler = new ExceptionAdviceHandler();
    }

    @Test
    void handlerWebClientResponseException() {
        ResponseEntity<Object> responseEntity = this.exceptionAdviceHandler.handlerWebClientResponseException(new WebClientResponseException(500, "", new HttpHeaders(), "".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(500);
    }

    @Test
    void handlerException() throws JsonProcessingException {
        ServerWebExchange mocked = mock();
        ServerHttpResponse httpResponse = mock();
        when(mocked.getResponse()).thenReturn(httpResponse);
        when(httpResponse.isCommitted()).thenReturn(true);
        Mono<ResponseEntity<ApiError>> responseEntity = this.exceptionAdviceHandler.handlerException(new WebClientResponseException(500, "", new HttpHeaders(), "".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), mocked);
        assertThat(responseEntity).isNotNull();
    }
}