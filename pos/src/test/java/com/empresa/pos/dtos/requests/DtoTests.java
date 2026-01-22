package com.empresa.pos.dtos.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;

class DtoTests {
    private BeanTester beanTester;

    @BeforeEach
    void setUp() {
        this.beanTester = new BeanTester();
    }

    @Test
    void requests() {
        this.beanTester.testBean(PosCostMinReq.class);
        this.beanTester.testBean(PosCostRequest.class);
        this.beanTester.testBean(PosHashRequest.class);
    }
}