package com.empresa.core.aop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.empresa.core.dtos.requests.PostHashPutRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
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
        JoinPoint joinPoint = mock();
        when(joinPoint.getArgs()).thenReturn(new Object[]{});
        when(joinPoint.getKind()).thenReturn("hello");
        when(joinPoint.getSignature()).thenReturn(mock(Signature.class));
        this.aop.logEnd(joinPoint);
    }

    @Test
    void logReturn() {
        this.aop.logReturn(mock(), new PostHashPutRequest());
    }
}