package com.empresa.core.aop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.empresa.core.dtos.requests.PostHashPutRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AopLoggerTest {

    private AopLogger aop;

    @BeforeEach
    void setUp() {
        this.aop = new AopLogger();

    }

    @Test
    void getaa() {
        this.aop.getaa();
    }

    @Test
    void logStar() {
        this.aop.logStar(mock());
    }

    @Test
    void logEnd() {
        this.aop.logEnd(mock());
    }

    @Test
    void logReturn() {
        this.aop.logReturn(mock(), new PostHashPutRequest());
    }
}