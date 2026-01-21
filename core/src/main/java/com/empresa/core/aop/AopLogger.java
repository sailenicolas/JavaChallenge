package com.empresa.core.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopLogger.class);


    @Pointcut("@within(com.empresa.core.annotations.LogStartClose)")
    public void getaa() {
    }
    @Before("getaa()")
    public void logStar(JoinPoint joinPoint){
        LOGGER.debug("Init {} {} {}", joinPoint.getArgs(), joinPoint.getKind(), joinPoint.getSignature());
    }
    @After("getaa()")
    public void logEnd(JoinPoint joinPoint){
        LOGGER.debug("end {} {} {}", joinPoint.getArgs(), joinPoint.getKind(), joinPoint.getSignature());
    }
    @AfterReturning(pointcut = "getaa()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){
        LOGGER.debug("return aa {}", result);
    }
}
