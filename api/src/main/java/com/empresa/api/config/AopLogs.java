package com.empresa.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.empresa.core.aop"})
public class AopLogs {
}
