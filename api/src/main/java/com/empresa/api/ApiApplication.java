package com.empresa.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import reactor.core.publisher.Hooks;

@SpringBootApplication(scanBasePackages = {"com.empresa"})
@EnableCaching
@EnableAspectJAutoProxy
public class ApiApplication {
    public static void main(String[] args) {
        Hooks.enableAutomaticContextPropagation();
        SpringApplication.run(ApiApplication.class, args);
    }
}